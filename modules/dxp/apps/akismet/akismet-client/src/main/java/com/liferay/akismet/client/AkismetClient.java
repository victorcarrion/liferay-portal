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

package com.liferay.akismet.client;

import com.liferay.akismet.client.constants.AkismetConstants;
import com.liferay.akismet.client.util.AkismetServiceConfigurationUtil;
import com.liferay.akismet.model.AkismetEntry;
import com.liferay.akismet.service.AkismetEntryLocalService;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jamie Sammons
 */
@Component(immediate = true, service = AkismetClient.class)
public class AkismetClient {

	public boolean hasRequiredInfo(String userIP, Map<String, String> headers) {
		if (headers == null) {
			return false;
		}

		String userAgent = headers.get(
			StringUtil.toLowerCase(HttpHeaders.USER_AGENT));

		if (Validator.isNull(userAgent)) {
			return false;
		}

		if (Validator.isNull(userIP)) {
			return false;
		}

		return true;
	}

	public boolean isSpam(
			long userId, String content, AkismetEntry akismetEntry)
		throws PortalException {

		StringBundler sb = new StringBundler(5);

		sb.append(Http.HTTP_WITH_SLASH);
		sb.append(AkismetServiceConfigurationUtil.getAPIKey());
		sb.append(StringPool.PERIOD);
		sb.append(AkismetConstants.URL_REST);
		sb.append(AkismetConstants.PATH_CHECK_SPAM);

		String location = sb.toString();

		User user = _userLocalService.getUser(userId);

		String response = _sendRequest(
			location, user.getCompanyId(), akismetEntry.getUserIP(),
			akismetEntry.getUserAgent(), akismetEntry.getReferrer(),
			akismetEntry.getPermalink(), akismetEntry.getType(),
			user.getFullName(), user.getEmailAddress(), content);

		if (Validator.isNull(response) || response.equals("invalid")) {
			_log.error("There was an issue with Akismet comment validation");

			return false;
		}
		else if (response.equals("true")) {
			if (_log.isDebugEnabled()) {
				_log.debug("Spam detected: " + akismetEntry.getPermalink());
			}

			return true;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Passed: " + akismetEntry.getPermalink());
		}

		return false;
	}

	public void submitHam(
			long companyId, String ipAddress, String userAgent, String referrer,
			String permalink, String commentType, String userName,
			String emailAddress, String content)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug("Submitting message as ham: " + permalink);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(Http.HTTP_WITH_SLASH);
		sb.append(AkismetServiceConfigurationUtil.getAPIKey());
		sb.append(StringPool.PERIOD);
		sb.append(AkismetConstants.URL_REST);
		sb.append(AkismetConstants.PATH_SUBMIT_HAM);

		String location = sb.toString();

		String response = _sendRequest(
			location, companyId, ipAddress, userAgent, referrer, permalink,
			commentType, userName, emailAddress, content);

		if (Validator.isNull(response)) {
			_log.error("There was an issue submitting message as ham");
		}
	}

	public void submitHam(MBMessage mbMessage) throws PortalException {
		AkismetEntry akismetEntry = _akismetEntryLocalService.fetchAkismetEntry(
			MBMessage.class.getName(), mbMessage.getMessageId());

		if (akismetEntry == null) {
			return;
		}

		User user = _userLocalService.getUser(mbMessage.getUserId());

		String content = mbMessage.getSubject() + "\n\n" + mbMessage.getBody();

		submitHam(
			mbMessage.getCompanyId(), akismetEntry.getUserIP(),
			akismetEntry.getUserAgent(), akismetEntry.getReferrer(),
			akismetEntry.getPermalink(), akismetEntry.getType(),
			user.getFullName(), user.getEmailAddress(), content);
	}

	public void submitSpam(
			long companyId, String ipAddress, String userAgent, String referrer,
			String permalink, String commentType, String userName,
			String emailAddress, String content)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug("Submitting message as spam: " + permalink);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(Http.HTTP_WITH_SLASH);
		sb.append(AkismetServiceConfigurationUtil.getAPIKey());
		sb.append(StringPool.PERIOD);
		sb.append(AkismetConstants.URL_REST);
		sb.append(AkismetConstants.PATH_SUBMIT_SPAM);

		String location = sb.toString();

		String response = _sendRequest(
			location, companyId, ipAddress, userAgent, referrer, permalink,
			commentType, userName, emailAddress, content);

		if (Validator.isNull(response) ||
			!verifyApiKey(
				companyId, AkismetServiceConfigurationUtil.getAPIKey())) {

			_log.error("There was an issue submitting message as spam");
		}
	}

	public void submitSpam(MBMessage mbMessage) throws PortalException {
		AkismetEntry akismetData = _akismetEntryLocalService.fetchAkismetEntry(
			MBMessage.class.getName(), mbMessage.getMessageId());

		if (akismetData == null) {
			return;
		}

		User user = _userLocalService.getUser(mbMessage.getUserId());

		String content = mbMessage.getSubject() + "\n\n" + mbMessage.getBody();

		submitSpam(
			mbMessage.getCompanyId(), akismetData.getUserIP(),
			akismetData.getUserAgent(), akismetData.getReferrer(),
			akismetData.getPermalink(), akismetData.getType(),
			user.getFullName(), user.getEmailAddress(), content);
	}

	public boolean verifyApiKey(long companyId, String apiKey)
		throws PortalException {

		String location =
			Http.HTTP_WITH_SLASH + AkismetConstants.URL_REST +
				AkismetConstants.PATH_VERIFY;

		Map<String, String> parts = new HashMap<>();

		parts.put("blog", _getPortalURL(companyId));
		parts.put("key", apiKey);

		String response = _sendRequest(location, parts);

		if (response.equals("valid")) {
			return true;
		}

		return false;
	}

	private String _getPortalURL(long companyId) throws PortalException {
		Company company = _companyLocalService.getCompany(companyId);

		return _portal.getPortalURL(
			company.getVirtualHostname(), _portal.getPortalServerPort(false),
			false);
	}

	private String _sendRequest(
			String location, long companyId, String ipAddress, String userAgent,
			String referrer, String permalink, String commentType,
			String userName, String emailAddress, String content)
		throws PortalException {

		Map<String, String> parts = new HashMap<>();

		parts.put("blog", _getPortalURL(companyId));
		parts.put("comment_author", userName);
		parts.put("comment_author_email", emailAddress);
		parts.put("comment_content", content);
		parts.put("comment_type", commentType);
		parts.put("permalink", permalink);
		parts.put("referrer", referrer);
		parts.put("user_agent", userAgent);
		parts.put("user_ip", ipAddress);

		return _sendRequest(location, parts);
	}

	private String _sendRequest(String location, Map<String, String> parts) {
		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.USER_AGENT, "Akismet/2.5.3");
		options.setLocation(location);
		options.setParts(parts);
		options.setPost(true);

		try {
			return _http.URLtoString(options);
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(AkismetClient.class);

	@Reference
	private AkismetEntryLocalService _akismetEntryLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}