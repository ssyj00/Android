/**
 * <p>文件名:		GID.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		CompanyTag</p>
 * @author		佘士东(sheshidong@lingtu.com, letifly@21cn.com)
 */

package com.ceb.dcpms.android.utils;

import java.util.UUID;

/**
 * <p>
 * GID
 * </p>
 * <p>
 * GAS唯一id生成器，由此工具生成的id能保证不会存在重复
 * </p>
 * 
 * @author 佘士东(sheshidong@lingtu.com, letifly@21cn.com)
 * @version 0.0.0
 *          <table style="border:1px solid gray;">
 *          <tr>
 *          <th width="100px">版本号</th><th width="100px">动作</th><th
 *          width="100px">修改人</th><th width="100px">修改时间</th>
 *          </tr>
 *          <!-- 以 Table 方式书写修改历史 -->
 *          <tr>
 *          <td>0.0.0</td>
 *          <td>创建类</td>
 *          <td>佘士东</td>
 *          <td>2008-10-10 上午11:37:17</td>
 *          </tr>
 *          <tr>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          </tr>
 *          </table>
 */
public class GID {

	/** 模块标识符号 */
	public static final int MODULE_ID_LEN = 5;

	/** 实体标识id，由实体统一制定 */
	public static final int ENTITY_ID_LEN = 5;

	/** 划分符号 */
	public static final char SEPARATE_CHAR = '-';

	/** 当输入串未达到长度的时候填充的内容 */
	public static final String FILL_CHAR_STRING = "xxxxx";

	/** 节点标识 */
	private static char NodeId = 'X';

	/** 实体标识名 */
	private static String EntityId;

	/** 模块标识名 */
	private static String ModuleId;

	/**
	 * 构造器
	 */
	private GID() {

	}

	/**
	 * FIXME
	 * 
	 * @param nodeId
	 * @param entityId
	 * @param moduleId
	 */
	public static void init(char nodeId, String entityId, String moduleId) {
		NodeId = nodeId;
		EntityId = entityId;
		ModuleId = moduleId;
	}

	/**
	 * 生成唯一的序列，固定长度50个ascii的alpha字符，任何两次生成的均不相同
	 * 
	 * @param nodeId
	 *            节点标识，用来区分不同的部署整体
	 * @param entityId
	 *            实体标识id，提供5个，如果不足5个补齐，如果超过则只要头部的5个
	 * @param moduleId
	 *            模块标识id，提供5个，如果不足5个则补齐，如果超过则只要头部的5个字符
	 * @return 返回格式为"UUID-nodeId-moduleId-entityId"
	 */
	public static final String gid(char nodeId, String entityId,
			String moduleId) {
		entityId = StringUtils.trim(entityId);
		int len = entityId.length();
		if (len < ENTITY_ID_LEN) {
			entityId += FILL_CHAR_STRING.substring(len);
		} else if (len > ENTITY_ID_LEN) {
			entityId = entityId.substring(0, ENTITY_ID_LEN);
		}

		moduleId = StringUtils.trim(moduleId);
		len = moduleId.length();
		if (len < MODULE_ID_LEN) {
			moduleId = FILL_CHAR_STRING.substring(len) + moduleId;
		} else if (len > MODULE_ID_LEN) {
			moduleId = moduleId.substring(0, MODULE_ID_LEN);
		}

		return "" + UUID.randomUUID() + SEPARATE_CHAR + nodeId + SEPARATE_CHAR
				+ moduleId + SEPARATE_CHAR + entityId;
	}

	/**
	 * 使用默认的
	 * 
	 * @param entityId
	 * @param moduleId
	 * @return
	 */
	public static final String gid(String entityId, String moduleId) {
		return gid(NodeId, entityId, moduleId);
	}

	/**
	 * FIXME
	 * 
	 * @param entityId
	 * @return
	 */
	public static final String gid(String entityId) {
		return gid(NodeId, entityId, ModuleId);
	}

	/**
	 * FIXME
	 * 
	 * @return
	 */
	public static final String gid() {
		return gid(NodeId, EntityId, ModuleId);
	}

}
