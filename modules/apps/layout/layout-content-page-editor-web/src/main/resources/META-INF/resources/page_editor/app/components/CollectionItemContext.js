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

import React, {useCallback, useContext} from 'react';

import InfoItemService from '../services/InfoItemService';

const defaultFromControlsId = itemId => itemId;
const defaultToControlsId = controlId => controlId;

const INITIAL_STATE = {
	canElevate: null,
	collectionFields: null,
	collectionItem: null,
	fromControlsId: defaultFromControlsId,
	toControlsId: defaultToControlsId,
};

const CollectionItemContext = React.createContext(INITIAL_STATE);

const CollectionItemContextProvider = CollectionItemContext.Provider;

const useFromControlsId = () => {
	const context = useContext(CollectionItemContext);

	return context.fromControlsId || defaultFromControlsId;
};

const useCanElevate = () => {
	const context = useContext(CollectionItemContext);

	return context.canElevate;
};

const useToControlsId = () => {
	const context = useContext(CollectionItemContext);

	return context.toControlsId || defaultToControlsId;
};

const useCollectionFields = () => {
	const context = useContext(CollectionItemContext);

	return context.collectionFields;
};

const useGetFieldValue = () => {
	const context = useContext(CollectionItemContext);

	const getFromServer = useCallback(
		({classNameId, classPK, fieldId, languageId}) =>
			InfoItemService.getAssetFieldValue({
				classNameId,
				classPK,
				fieldId,
				languageId,
				onNetworkStatus: () => {},
			}).then(response => {
				const {fieldValue = ''} = response;

				return fieldValue;
			}),
		[]
	);

	const getFromCollectionItem = useCallback(
		({collectionFieldId}) =>
			context.collectionItem[collectionFieldId] !== null &&
			context.collectionItem[collectionFieldId] !== undefined
				? Promise.resolve(context.collectionItem[collectionFieldId])
				: Promise.reject(),
		[context.collectionItem]
	);

	if (context.collectionFields !== null && context.collectionItem !== null) {
		return getFromCollectionItem;
	}
	else {
		return getFromServer;
	}
};

export {
	CollectionItemContext,
	CollectionItemContextProvider,
	useCanElevate,
	useCollectionFields,
	useFromControlsId,
	useToControlsId,
	useGetFieldValue,
};
