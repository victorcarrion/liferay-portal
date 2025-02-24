/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardThread;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.SearchTestRule;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class MessageBoardThreadResourceTest
	extends BaseMessageBoardThreadResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		_mbCategory = MBCategoryLocalServiceUtil.addCategory(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Ignore
	@Override
	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPageWithSortInteger() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMessageBoardThreadsRankedPage() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMessageBoardThreadsRankedPageWithPagination() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMessageBoardThreadsRankedPageWithSortDateTime() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMessageBoardThreadsRankedPageWithSortInteger() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMessageBoardThreadsRankedPageWithSortString() {
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteMessageBoardThreadByFriendlyUrlPath() {
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteMessageBoardThreadsPageWithSortInteger() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteMessageBoardThreadByFriendlyUrlPath() {
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"articleBody", "headline"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId", "messageBoardSectionId"};
	}

	@Override
	protected MessageBoardThread randomMessageBoardThread() throws Exception {
		MessageBoardThread messageBoardThread =
			super.randomMessageBoardThread();

		messageBoardThread.setMessageBoardSectionId((Long)null);
		messageBoardThread.setThreadType("Urgent");

		return messageBoardThread;
	}

	@Override
	protected Long
		testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId() {

		return _mbCategory.getCategoryId();
	}

	private MBCategory _mbCategory;

}