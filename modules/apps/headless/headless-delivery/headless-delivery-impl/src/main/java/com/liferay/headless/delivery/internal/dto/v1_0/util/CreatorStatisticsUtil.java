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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.CreatorStatistics;
import com.liferay.message.boards.service.MBStatsUserLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

/**
 * @author Luis Miguel Barcos
 */
public class CreatorStatisticsUtil {

	public static CreatorStatistics toCreatorStatistics(
			MBStatsUserLocalService mbStatsUserLocalService, String languageId,
			User user)
		throws PortalException {

		String[] ranks = mbStatsUserLocalService.getUserRank(
			user.getGroupId(), languageId, user.getUserId());

		return new CreatorStatistics() {
			{
				joinDate = user.getCreateDate();
				lastPostDate = mbStatsUserLocalService.getLastPostDateByUserId(
					user.getGroupId(), user.getUserId());
				postsNumber = Math.toIntExact(
					mbStatsUserLocalService.getMessageCountByUserId(
						user.getUserId()));
				rank = ranks[1].equals(StringPool.BLANK) ? ranks[0] : ranks[1];
			}
		};
	}

}