/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.akismet.internal.service;

import com.liferay.akismet.client.AkismetClient;
import com.liferay.akismet.client.constants.AkismetConstants;
import com.liferay.akismet.client.util.AkismetServiceConfigurationUtil;
import com.liferay.akismet.model.AkismetEntry;
import com.liferay.akismet.service.AkismetEntryLocalService;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.InputStream;
import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jamie Sammons
 */
@Component(immediate = true, property = {}, service = ServiceWrapper.class)
public class AkismetMBMessageLocalServiceWrapper
	extends MBMessageLocalServiceWrapper {

	public AkismetMBMessageLocalServiceWrapper() {
		super(null);
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			long threadId, long parentMessageId, String subject, String body,
			String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		MBMessage message = super.addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, format, inputStreamOVPs, anonymous, priority,
			allowPingbacks, serviceContext);

		if (!_isCheckSpamEnabled(userId, groupId, serviceContext)) {
			return message;
		}

		int status = _checkSpam(message.getMessageId(), serviceContext);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			super.updateMessage(
				userId, message.getMessageId(), message.getBody(),
				serviceContext);
		}
		else {
			super.updateStatus(
				userId, message.getMessageId(), status, serviceContext,
				new HashMap<String, Serializable>());
		}

		return message;
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		return addMessage(
			userId, userName, groupId, categoryId, 0,
			MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, subject, body, format,
			inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);
	}

	private int _checkSpam(long messageId, ServiceContext serviceContext)
		throws PortalException {

		MBMessage message = _mbMessageLocalService.getMBMessage(messageId);

		if (_isCheckSpamEnabled(
				message.getUserId(), message.getGroupId(), serviceContext)) {

			AkismetEntry akismetEntry = _updateAkismetEntry(
				message, serviceContext);

			String content = message.getSubject() + "\n\n" + message.getBody();

			if (_akismetClient.isSpam(
					message.getUserId(), content, akismetEntry)) {

				return WorkflowConstants.STATUS_DENIED;
			}
		}

		return WorkflowConstants.STATUS_APPROVED;
	}

	private String _getPermalink(
		MBMessage message, ServiceContext serviceContext) {

		String contentURL = (String)serviceContext.getAttribute("contentURL");

		if (Validator.isNotNull(contentURL)) {
			return contentURL;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(serviceContext.getPortalURL());
		sb.append(serviceContext.getPathMain());
		sb.append("/message_boards/find_entry?messageId=");
		sb.append(message.getMessageId());

		return sb.toString();
	}

	private boolean _isCheckSpamEnabled(
			long userId, long groupId, ServiceContext serviceContext)
		throws PortalException {

		if (!AkismetServiceConfigurationUtil.isMessageBoardsEnabled()) {
			return false;
		}

		if (!_akismetClient.hasRequiredInfo(
				serviceContext.getRemoteAddr(), serviceContext.getHeaders())) {

			return false;
		}

		int checkThreshold =
			AkismetServiceConfigurationUtil.getAkismetCheckThreshold();

		if (checkThreshold > 0) {
			int count = super.getGroupMessagesCount(
				groupId, userId, WorkflowConstants.STATUS_APPROVED);

			if (count > checkThreshold) {
				return false;
			}
		}

		return true;
	}

	private AkismetEntry _updateAkismetEntry(
		MBMessage message, ServiceContext serviceContext) {

		String permalink = _getPermalink(message, serviceContext);

		Map<String, String> headers = serviceContext.getHeaders();

		String referrer = headers.get("referer");
		String userAgent = headers.get(
			StringUtil.toLowerCase(HttpHeaders.USER_AGENT));

		String userIP = serviceContext.getRemoteAddr();

		return _akismetEntryLocalService.updateAkismetEntry(
			MBMessage.class.getName(), message.getMessageId(),
			AkismetConstants.TYPE_COMMENT, permalink, referrer, userAgent,
			userIP, StringPool.BLANK);
	}

	@Reference
	private AkismetClient _akismetClient;

	@Reference
	private AkismetEntryLocalService _akismetEntryLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

}