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

package com.liferay.journal.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for JournalContentSearch. This utility wraps
 * <code>com.liferay.journal.service.impl.JournalContentSearchLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see JournalContentSearchLocalService
 * @generated
 */
public class JournalContentSearchLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.journal.service.impl.JournalContentSearchLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the journal content search to the database. Also notifies the appropriate model listeners.
	 *
	 * @param journalContentSearch the journal content search
	 * @return the journal content search that was added
	 */
	public static com.liferay.journal.model.JournalContentSearch
		addJournalContentSearch(
			com.liferay.journal.model.JournalContentSearch
				journalContentSearch) {

		return getService().addJournalContentSearch(journalContentSearch);
	}

	public static void checkContentSearches(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkContentSearches(companyId);
	}

	/**
	 * Creates a new journal content search with the primary key. Does not add the journal content search to the database.
	 *
	 * @param contentSearchId the primary key for the new journal content search
	 * @return the new journal content search
	 */
	public static com.liferay.journal.model.JournalContentSearch
		createJournalContentSearch(long contentSearchId) {

		return getService().createJournalContentSearch(contentSearchId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static void deleteArticleContentSearch(
		long groupId, boolean privateLayout, long layoutId, String portletId) {

		getService().deleteArticleContentSearch(
			groupId, privateLayout, layoutId, portletId);
	}

	public static void deleteArticleContentSearch(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		String articleId) {

		getService().deleteArticleContentSearch(
			groupId, privateLayout, layoutId, portletId, articleId);
	}

	public static void deleteArticleContentSearches(
		long groupId, String articleId) {

		getService().deleteArticleContentSearches(groupId, articleId);
	}

	/**
	 * Deletes the journal content search from the database. Also notifies the appropriate model listeners.
	 *
	 * @param journalContentSearch the journal content search
	 * @return the journal content search that was removed
	 */
	public static com.liferay.journal.model.JournalContentSearch
		deleteJournalContentSearch(
			com.liferay.journal.model.JournalContentSearch
				journalContentSearch) {

		return getService().deleteJournalContentSearch(journalContentSearch);
	}

	/**
	 * Deletes the journal content search with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search that was removed
	 * @throws PortalException if a journal content search with the primary key could not be found
	 */
	public static com.liferay.journal.model.JournalContentSearch
			deleteJournalContentSearch(long contentSearchId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteJournalContentSearch(contentSearchId);
	}

	public static void deleteLayoutContentSearches(
		long groupId, boolean privateLayout, long layoutId) {

		getService().deleteLayoutContentSearches(
			groupId, privateLayout, layoutId);
	}

	public static void deleteOwnerContentSearches(
		long groupId, boolean privateLayout) {

		getService().deleteOwnerContentSearches(groupId, privateLayout);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.journal.model.impl.JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.journal.model.impl.JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.journal.model.JournalContentSearch
		fetchJournalContentSearch(long contentSearchId) {

		return getService().fetchJournalContentSearch(contentSearchId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.journal.model.JournalContentSearch>
		getArticleContentSearches() {

		return getService().getArticleContentSearches();
	}

	public static java.util.List<com.liferay.journal.model.JournalContentSearch>
		getArticleContentSearches(long groupId, String articleId) {

		return getService().getArticleContentSearches(groupId, articleId);
	}

	public static java.util.List<com.liferay.journal.model.JournalContentSearch>
		getArticleContentSearches(String articleId) {

		return getService().getArticleContentSearches(articleId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the journal content search with the primary key.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search
	 * @throws PortalException if a journal content search with the primary key could not be found
	 */
	public static com.liferay.journal.model.JournalContentSearch
			getJournalContentSearch(long contentSearchId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getJournalContentSearch(contentSearchId);
	}

	/**
	 * Returns a range of all the journal content searches.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.journal.model.impl.JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of journal content searches
	 */
	public static java.util.List<com.liferay.journal.model.JournalContentSearch>
		getJournalContentSearchs(int start, int end) {

		return getService().getJournalContentSearchs(start, end);
	}

	/**
	 * Returns the number of journal content searches.
	 *
	 * @return the number of journal content searches
	 */
	public static int getJournalContentSearchsCount() {
		return getService().getJournalContentSearchsCount();
	}

	public static java.util.List<Long> getLayoutIds(
		long groupId, boolean privateLayout, String articleId) {

		return getService().getLayoutIds(groupId, privateLayout, articleId);
	}

	public static int getLayoutIdsCount(
		long groupId, boolean privateLayout, String articleId) {

		return getService().getLayoutIdsCount(
			groupId, privateLayout, articleId);
	}

	public static int getLayoutIdsCount(String articleId) {
		return getService().getLayoutIdsCount(articleId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.journal.model.JournalContentSearch>
		getPortletContentSearches(String portletId) {

		return getService().getPortletContentSearches(portletId);
	}

	public static com.liferay.journal.model.JournalContentSearch
			updateContentSearch(
				long groupId, boolean privateLayout, long layoutId,
				String portletId, String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateContentSearch(
			groupId, privateLayout, layoutId, portletId, articleId);
	}

	public static com.liferay.journal.model.JournalContentSearch
			updateContentSearch(
				long groupId, boolean privateLayout, long layoutId,
				String portletId, String articleId, boolean purge)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateContentSearch(
			groupId, privateLayout, layoutId, portletId, articleId, purge);
	}

	public static java.util.List<com.liferay.journal.model.JournalContentSearch>
			updateContentSearch(
				long groupId, boolean privateLayout, long layoutId,
				String portletId, String[] articleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateContentSearch(
			groupId, privateLayout, layoutId, portletId, articleIds);
	}

	/**
	 * Updates the journal content search in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param journalContentSearch the journal content search
	 * @return the journal content search that was updated
	 */
	public static com.liferay.journal.model.JournalContentSearch
		updateJournalContentSearch(
			com.liferay.journal.model.JournalContentSearch
				journalContentSearch) {

		return getService().updateJournalContentSearch(journalContentSearch);
	}

	public static JournalContentSearchLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<JournalContentSearchLocalService, JournalContentSearchLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			JournalContentSearchLocalService.class);

		ServiceTracker
			<JournalContentSearchLocalService, JournalContentSearchLocalService>
				serviceTracker =
					new ServiceTracker
						<JournalContentSearchLocalService,
						 JournalContentSearchLocalService>(
							 bundle.getBundleContext(),
							 JournalContentSearchLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}