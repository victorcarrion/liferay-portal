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

package com.liferay.portal.workflow.metrics.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@GraphQLName("AssigneeBulkSelection")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AssigneeBulkSelection")
public class AssigneeBulkSelection {

	@Schema
	public Long[] getInstanceIds() {
		return instanceIds;
	}

	public void setInstanceIds(Long[] instanceIds) {
		this.instanceIds = instanceIds;
	}

	@JsonIgnore
	public void setInstanceIds(
		UnsafeSupplier<Long[], Exception> instanceIdsUnsafeSupplier) {

		try {
			instanceIds = instanceIdsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long[] instanceIds;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssigneeBulkSelection)) {
			return false;
		}

		AssigneeBulkSelection assigneeBulkSelection =
			(AssigneeBulkSelection)object;

		return Objects.equals(toString(), assigneeBulkSelection.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (instanceIds != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"instanceIds\": ");

			sb.append("[");

			for (int i = 0; i < instanceIds.length; i++) {
				sb.append(instanceIds[i]);

				if ((i + 1) < instanceIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.portal.workflow.metrics.rest.dto.v1_0.AssigneeBulkSelection",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}