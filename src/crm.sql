/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2018-09-20 22:46:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_clue
-- ----------------------------
DROP TABLE IF EXISTS `tbl_clue`;
CREATE TABLE `tbl_clue` (
  `id` char(32) NOT NULL COMMENT '线索标识',
  `pid_user` char(32) DEFAULT NULL COMMENT '线索所有者标识',
  `company` varchar(255) DEFAULT NULL COMMENT '公司',
  `calls` varchar(255) DEFAULT NULL COMMENT '称呼',
  `surname` varchar(255) DEFAULT NULL COMMENT '姓名',
  `job` varchar(255) DEFAULT NULL COMMENT '职位',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `phone` char(12) DEFAULT NULL COMMENT '手机号',
  `state` varchar(255) DEFAULT NULL COMMENT '线索状态',
  `source` varchar(255) DEFAULT NULL COMMENT '线索来源',
  `description` varchar(255) DEFAULT NULL COMMENT '线索描述',
  `contactSummary` varchar(255) DEFAULT NULL COMMENT '联系纪要',
  `nextContactTime` varchar(255) DEFAULT NULL COMMENT '下次联系时间',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `website` varchar(255) DEFAULT NULL COMMENT '公司网站',
  `tel` char(14) DEFAULT NULL COMMENT '公司座机',
  `createTime` char(19) DEFAULT '',
  `creater` varchar(255) DEFAULT NULL COMMENT '创建者姓名',
  `editer` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线索表';

-- ----------------------------
-- Records of tbl_clue
-- ----------------------------
INSERT INTO `tbl_clue` VALUES ('0e07957563624a77a1d456264aedd18d', '4', '三星', '先生', '张风', 'java', '', '1234', '将来联系', '广告', '', '', '2018-07-04', '', 'www.51cto.com', '1234', '2018-07-24 21:54:11', null, null, '2018-07-25 20:29:01');
INSERT INTO `tbl_clue` VALUES ('ae5269780c30443f93113515e806be4b', '9', '', '夫人', 'aaa', '', '', '', '外部介绍', '已联系', '', '', '', '', '', '', '2018-08-01 14:30:57', null, null, null);
INSERT INTO `tbl_clue` VALUES ('f1f54cd4b12849d484b7fe9e2524add4', '1', 'sun1', '夫人', '孙电', '人事', 'sundian@sun.com', '123456789', '已联系', '广告', null, '孙电孙电孙电', '2020-09-09', '孙电孙电孙电孙电', 'www.sum.com', '100-100-100', '2018-07-25 10:49:19', '张三', null, '2018-08-05 20:07:51');

-- ----------------------------
-- Table structure for tbl_clue_market_relation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_clue_market_relation`;
CREATE TABLE `tbl_clue_market_relation` (
  `id` char(32) NOT NULL,
  `p_marketId` char(32) DEFAULT NULL COMMENT '市场活动标识',
  `p_clueId` char(32) DEFAULT NULL COMMENT '线索标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线索市场活动关系表';

-- ----------------------------
-- Records of tbl_clue_market_relation
-- ----------------------------
INSERT INTO `tbl_clue_market_relation` VALUES ('2', '2', '2');
INSERT INTO `tbl_clue_market_relation` VALUES ('6f7282d44ab349a6a6361c654fbeb963', '1', '0e07957563624a77a1d456264aedd18d');
INSERT INTO `tbl_clue_market_relation` VALUES ('con', '1', '0e07957563624a77a1d456264aedd18d');
INSERT INTO `tbl_clue_market_relation` VALUES ('f551cac5762d40128303177e9ce700ba', '888', 'f1f54cd4b12849d484b7fe9e2524add4');

-- ----------------------------
-- Table structure for tbl_clue_remark
-- ----------------------------
DROP TABLE IF EXISTS `tbl_clue_remark`;
CREATE TABLE `tbl_clue_remark` (
  `id` char(32) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` varchar(255) DEFAULT NULL,
  `editFlag` char(1) DEFAULT NULL,
  `p_clueId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线索备注表';

-- ----------------------------
-- Records of tbl_clue_remark
-- ----------------------------
INSERT INTO `tbl_clue_remark` VALUES ('461de4dcd51544828612d8d64d01e118', '4536453asdasd', 'xxx', '2018-08-05 20:30:22', 'xxx', '2018-08-05 20:32:39', '1', 'ae5269780c30443f93113515e806be4b');
INSERT INTO `tbl_clue_remark` VALUES ('75bde2de48df4b19927d4c3614af8d64', '123', 'xxx', '2018-07-26 20:15:07', null, null, '0', 'f1f54cd4b12849d484b7fe9e2524add4');
INSERT INTO `tbl_clue_remark` VALUES ('ddbbecb1d1824741a32719d326390ace', '000121', null, '2018-07-24 22:02:55', null, '2018-07-24 22:11:58', '1', '0e07957563624a77a1d456264aedd18d');

-- ----------------------------
-- Table structure for tbl_contacts
-- ----------------------------
DROP TABLE IF EXISTS `tbl_contacts`;
CREATE TABLE `tbl_contacts` (
  `id` char(32) NOT NULL,
  `owner` char(32) DEFAULT NULL COMMENT '联系人所有者id',
  `source` varchar(255) DEFAULT NULL COMMENT '联系人来源',
  `customerId` char(32) DEFAULT NULL COMMENT '客户id',
  `fullname` varchar(255) DEFAULT NULL COMMENT '姓名',
  `appellation` varchar(255) DEFAULT NULL COMMENT '称呼',
  `email` varchar(255) DEFAULT NULL,
  `mphone` varchar(255) DEFAULT NULL COMMENT '手机',
  `job` varchar(255) DEFAULT NULL COMMENT '职位',
  `birth` char(10) DEFAULT NULL COMMENT '生日',
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `contactSummary` varchar(255) DEFAULT NULL COMMENT '联系纪要',
  `nextContactTime` char(10) DEFAULT NULL COMMENT '下次联系时间',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人表';

-- ----------------------------
-- Records of tbl_contacts
-- ----------------------------
INSERT INTO `tbl_contacts` VALUES ('1', '2', '推销电话', '1', '孙钱', '教授', 'sunqian@xx.com', '17808080202', '', '2018-07-11', 'aq', '2018-07-27 16:11:44', '没有登录，自动添加测试用户', '2018-07-27 15:57:28', '', '', '', '');
INSERT INTO `tbl_contacts` VALUES ('1962c39b354346ac8ec4a823667ffa13', '3', '广告', '4d9c0b61ccaf4d95b53471a795384155', '123', '先生', '', '', '', '', '没有登录，测试用例', '2018-07-29 16:45:39', 'xxx', '2018-08-01 16:38:12', '', '', '', '北京大兴区大族企业湾');
INSERT INTO `tbl_contacts` VALUES ('2', '5', '推销电话', '82a76255fe3c4eed9f332be9448e1311', '刘风', '教授', 'liufeng@xx.com', '13512112111', '000', '2018-09-20', '000', '2018-07-26 16:11:44', 'xxx', '2018-07-26 19:33:15', '00s', 'ss', '1899-12-31', '北京大兴区大族企业湾');
INSERT INTO `tbl_contacts` VALUES ('bf7383f7e3a24b1484b2b7d1807b0534', '3', '员工介绍', '518b3a2c1c1446028f336e242d0d7240', 'aq', '先生', 'aq@jd.com', '111', 'java', '2008-08-08', '没有登录，测试用例', '2018-07-29 16:37:09', '没有登录，自动添加测试用户', '2018-07-30 23:25:54', 'jd线索描述', 'jd联系纪要', '1899-12-09', '北京大兴');

-- ----------------------------
-- Table structure for tbl_contacts_activity_relation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_contacts_activity_relation`;
CREATE TABLE `tbl_contacts_activity_relation` (
  `id` char(32) NOT NULL,
  `contactId` char(32) DEFAULT NULL COMMENT '联系人标识',
  `activityId` char(32) DEFAULT NULL COMMENT '市场活动标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人市场活动关系表';

-- ----------------------------
-- Records of tbl_contacts_activity_relation
-- ----------------------------
INSERT INTO `tbl_contacts_activity_relation` VALUES ('a036584a06d941fb9dc387c6ecbbe2a1', '1', '74b23d8a1dea4d66a446a39b62a8806f');
INSERT INTO `tbl_contacts_activity_relation` VALUES ('a0ee57494497405ea0b9a4eddba7ca6a', '2', '999');
INSERT INTO `tbl_contacts_activity_relation` VALUES ('d04110b3b7eb42f685b235c93b8deb29', '14c4d700136645b688ea41e82a63aa31', '1');
INSERT INTO `tbl_contacts_activity_relation` VALUES ('d3fa2e47648f417d826c8d6beff8ccb2', 'bf7383f7e3a24b1484b2b7d1807b0534', '1');

-- ----------------------------
-- Table structure for tbl_contacts_remark
-- ----------------------------
DROP TABLE IF EXISTS `tbl_contacts_remark`;
CREATE TABLE `tbl_contacts_remark` (
  `id` char(32) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `editFlag` char(1) DEFAULT NULL,
  `contactId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人备注表';

-- ----------------------------
-- Records of tbl_contacts_remark
-- ----------------------------
INSERT INTO `tbl_contacts_remark` VALUES ('256a1091fa11413ead02c162c6bef4a0', 'jd备注', '没有登录，测试用例', '2018-07-29 16:36:50', null, null, '0', 'bf7383f7e3a24b1484b2b7d1807b0534');
INSERT INTO `tbl_contacts_remark` VALUES ('91763122a7e44ea4a77a7fb7a9daa182', 'asd', '没有登录，测试用例', '2018-07-29 18:30:07', null, null, '0', '1962c39b354346ac8ec4a823667ffa13');
INSERT INTO `tbl_contacts_remark` VALUES ('9e0658c69b9a4f06961769b5b06d3332', 'asd', 'xxx', '2018-07-29 18:30:44', null, null, '0', '1962c39b354346ac8ec4a823667ffa13');
INSERT INTO `tbl_contacts_remark` VALUES ('abdb4d7c320842d8b057811c4a075fcc', 'asd', 'xxx', '2018-07-26 21:28:55', null, null, '0', '2');
INSERT INTO `tbl_contacts_remark` VALUES ('c9473ae4a06d4af0871a7de7dac76bc4', '12asds', 'xxx', '2018-07-26 20:57:16', 'xxx', '2018-07-26 21:28:49', '1', '2');
INSERT INTO `tbl_contacts_remark` VALUES ('d3342161c8654e14a965440a6b09f9a5', '000qq', 'xxx', '2018-07-26 21:31:46', '', '2018-07-26 21:40:20', '1', '2');

-- ----------------------------
-- Table structure for tbl_customer
-- ----------------------------
DROP TABLE IF EXISTS `tbl_customer`;
CREATE TABLE `tbl_customer` (
  `id` char(32) NOT NULL,
  `owner` char(32) DEFAULT NULL COMMENT '客户所有者',
  `name` varchar(255) DEFAULT NULL COMMENT '公司姓名',
  `website` varchar(255) DEFAULT NULL COMMENT '公司网站',
  `phone` varchar(255) DEFAULT NULL COMMENT '公司座机',
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `contactSummary` varchar(255) DEFAULT NULL COMMENT '联系纪要',
  `nextContactTime` char(10) DEFAULT NULL COMMENT '下次联系时间',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户表';

-- ----------------------------
-- Records of tbl_customer
-- ----------------------------
INSERT INTO `tbl_customer` VALUES ('1', '2', '小米', 'asd', '111', '', '2018-07-26 09:24:14', '没有登录，测试用例', '2018-07-29 16:39:33', '', '', 'asd', '北京大兴大族企业湾');
INSERT INTO `tbl_customer` VALUES ('106f4605cb784c218788b4102d6939dc', '4', '兰亭', null, null, '没有登录，测试用例', '2018-07-30 17:40:04', null, null, '点灯测试点灯测试', '2018-07-19', '点灯测试', null);
INSERT INTO `tbl_customer` VALUES ('2', '4', '动力节点', 'http://www.bjpowernode.com', '010-84846003', '', '2018-07-25 09:59:07', '', '2018-07-25 17:15:33', '000', '000', '000', '北京大兴大族企业湾');
INSERT INTO `tbl_customer` VALUES ('58305ed91d9245b89e1183f7b8f734de', '9', '123', null, null, 'xxx', '2018-08-01 15:47:45', 'xxx', '2018-08-02 12:10:46', null, null, null, '北京大兴大族企业湾');
INSERT INTO `tbl_customer` VALUES ('7018a7eb331f4082aaa143c2e9e9f43f', '7', '咪咕', null, null, '没有登录，测试用例', '2018-07-30 14:39:19', null, null, '咪咕咪咕', '2018-07-26', '咪咕咪咕', null);
INSERT INTO `tbl_customer` VALUES ('82a76255fe3c4eed9f332be9448e1311', '1', '网易', null, null, '', '2018-07-26 16:11:44', 'xxx', '2018-07-26 19:33:15', null, null, null, null);
INSERT INTO `tbl_customer` VALUES ('dbbf76a41b0b41eea76dadbf42799b89', '5', '点灯测试', null, null, '没有登录，测试用例', '2018-07-30 17:54:10', null, null, '点灯测试', '2018-07-26', '点灯测试', null);

-- ----------------------------
-- Table structure for tbl_customer_remark
-- ----------------------------
DROP TABLE IF EXISTS `tbl_customer_remark`;
CREATE TABLE `tbl_customer_remark` (
  `id` char(32) NOT NULL,
  `noteContent` varchar(255) DEFAULT NULL COMMENT '备注内容',
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `editFlag` char(1) DEFAULT NULL COMMENT '修改标识:创建备注时默认为0,修改后为1',
  `customerId` char(32) DEFAULT NULL COMMENT '客户标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户备注表';

-- ----------------------------
-- Records of tbl_customer_remark
-- ----------------------------
INSERT INTO `tbl_customer_remark` VALUES ('0c442d3150bd4fb9b3b882da68d53c0e', '阿斯顿', 'xxx', '2018-08-12 14:04:58', null, null, '0', '2');
INSERT INTO `tbl_customer_remark` VALUES ('32960239676b4681a21085ced34240bc', 'jd备注', '没有登录，测试用例', '2018-07-29 16:36:50', null, null, '0', '518b3a2c1c1446028f336e242d0d7240');
INSERT INTO `tbl_customer_remark` VALUES ('3ce98152e12a4efcabbf4a3363bd93f1', '网易网易', 'aq', '2018-07-27 14:49:34', null, null, '0', '1');
INSERT INTO `tbl_customer_remark` VALUES ('6247d75307ec48e38aaf64a0c2ca4f22', 'asd', 'admin', '2018-07-25 21:23:54', null, null, '0', '2');
INSERT INTO `tbl_customer_remark` VALUES ('7824108fed504af791a1aedfbe4968e7', '小米小米', '', '2018-07-27 14:50:55', null, null, '0', '82a76255fe3c4eed9f332be9448e1311');
INSERT INTO `tbl_customer_remark` VALUES ('7f5c26092f9849e7913811d044b75e99', '啊实打实的', '没有登录，测试用例', '2018-07-29 18:15:54', null, null, '0', '1');
INSERT INTO `tbl_customer_remark` VALUES ('86a95623c49745d1bebc7802117e88f1', '123asd', 'xxx', '2018-08-12 14:33:30', '没有登录，测试用例', '2018-08-12 14:52:43', '1', '2');
INSERT INTO `tbl_customer_remark` VALUES ('c2533fae557045d19eb202dc2fdf5e4f', '000', '', '2018-07-27 15:46:05', '', '2018-07-27 15:46:20', '1', '82a76255fe3c4eed9f332be9448e1311');
INSERT INTO `tbl_customer_remark` VALUES ('d2d42e521d3b4e3a9f025017f2536278', 'asdasd', 'xxx', '2018-07-25 21:22:20', 'admin', '2018-07-25 21:23:49', '1', '2');
INSERT INTO `tbl_customer_remark` VALUES ('eb543a32e3ec4f91903e2b4f836cf2be', 'sss', '', '2018-07-27 14:49:39', null, null, '0', '2');

-- ----------------------------
-- Table structure for tbl_dept
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dept`;
CREATE TABLE `tbl_dept` (
  `deptno` varchar(4) DEFAULT NULL COMMENT '部门编号',
  `DNAME` varchar(14) DEFAULT NULL COMMENT '部门名称',
  `MASTER` char(10) DEFAULT NULL COMMENT '部门负责人',
  `TEL` char(12) DEFAULT NULL COMMENT '部门座机',
  `DESCRIPT` varchar(255) DEFAULT NULL COMMENT '部门描述',
  `id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `deptno` (`deptno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of tbl_dept
-- ----------------------------
INSERT INTO `tbl_dept` VALUES ('222', '222', '222', '1111', '1111', '12');
INSERT INTO `tbl_dept` VALUES ('0003', '0007', '0002', '0003', '0003', '19');
INSERT INTO `tbl_dept` VALUES ('0004', '研发部', '0004', '0004', '0004', '20');
INSERT INTO `tbl_dept` VALUES ('M001', '市场部', '王五2', '00058', '0005', '23');
INSERT INTO `tbl_dept` VALUES ('M002', '销售部', 'ww2', '010-00000003', '333', '3');
INSERT INTO `tbl_dept` VALUES ('M003', '策划部', 'ww', '33', '', '4');
INSERT INTO `tbl_dept` VALUES ('40', 'OPERATIONS', 'zl', '010-00000004', '444', '5');

-- ----------------------------
-- Table structure for tbl_dictionarytype
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dictionarytype`;
CREATE TABLE `tbl_dictionarytype` (
  `type` varchar(255) NOT NULL DEFAULT '' COMMENT '类型编码',
  `name` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `description` varchar(255) DEFAULT NULL COMMENT '类型描述',
  `id` char(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`type`,`id`),
  UNIQUE KEY `type` (`type`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型表';

-- ----------------------------
-- Records of tbl_dictionarytype
-- ----------------------------
INSERT INTO `tbl_dictionarytype` VALUES ('calls', '称呼', '包括先生、女士、夫人、博士、教授', '2');
INSERT INTO `tbl_dictionarytype` VALUES ('clueSource', '线索来源', '线索来源包括：广告,推销电话,员工介绍,外部介绍,在线商场,合作伙伴,公开媒介,销售邮件,合作伙伴研讨会,内部研讨会,交易会,web下载,web调研,聊天', '9b0dd860ecdc48b2965ccfd91f0bdd6f');
INSERT INTO `tbl_dictionarytype` VALUES ('clueState', '线索状态', '线索状态包括：试图联系,将来联系,已联系,虚假线索,丢失线索,未联系,需要条件', 'a2fbf5983ac04d2687f065e21f66d30b');
INSERT INTO `tbl_dictionarytype` VALUES ('f', '10', '9', '3');
INSERT INTO `tbl_dictionarytype` VALUES ('me2', 'a', 'bbbbbb', '4');
INSERT INTO `tbl_dictionarytype` VALUES ('orgType', '部门', '包括一级部门、二级部门、三级部门', '1');
INSERT INTO `tbl_dictionarytype` VALUES ('sex', '性别', '性别包括男和女', '5');
INSERT INTO `tbl_dictionarytype` VALUES ('transactionStage', '交易阶段', '交易阶段包括：资质审查,需求分析,价值建议,确定决策者,提案/报价,谈判/复审,成交,丢失的线索,因竞争丢失关闭', '4d346a17ebe64c888b76a9faaabf6d76');
INSERT INTO `tbl_dictionarytype` VALUES ('transType', '交易类型', '交易类型：已有业务、新业务', '6df438382a4042baa7467f586312bbc1');

-- ----------------------------
-- Table structure for tbl_dictionaryvalue
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dictionaryvalue`;
CREATE TABLE `tbl_dictionaryvalue` (
  `id` char(32) NOT NULL,
  `val` varchar(255) NOT NULL COMMENT '字典值',
  `content` varchar(255) DEFAULT NULL COMMENT '文本',
  `sortNo` int(32) DEFAULT NULL COMMENT '排序号',
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典值表';

-- ----------------------------
-- Records of tbl_dictionaryvalue
-- ----------------------------
INSERT INTO `tbl_dictionaryvalue` VALUES ('02dbe929308a4f49b5b4c7d4828d7f4c', 'professor', '教授', '4', 'calls');
INSERT INTO `tbl_dictionaryvalue` VALUES ('05f56b8e979c49c0bff772266e1cd374', 'has_contact', '已联系', '3', 'clueState');
INSERT INTO `tbl_dictionaryvalue` VALUES ('1cc2a323a9084539b238517c6fb714c6', 'try_to_contact', '试图联系', '1', 'clueState');
INSERT INTO `tbl_dictionaryvalue` VALUES ('2', 'f', '女', '2', 'sex');
INSERT INTO `tbl_dictionaryvalue` VALUES ('237e45c2af0342e68c1547a1fc4d5ad5', 'trans_estimate', '01资质审查', '1', 'transactionStage');
INSERT INTO `tbl_dictionaryvalue` VALUES ('2a4f85148ad5471ebbd1b0420087f6fa', 'success', '07成交', '7', 'transactionStage');
INSERT INTO `tbl_dictionaryvalue` VALUES ('32f0ece6cff14418ba62fffad2257764', '一级部门', '一级部门', '1', 'orgType');
INSERT INTO `tbl_dictionaryvalue` VALUES ('374f79a3d766402889b5432ab516788f', 'negotiate/check', '06谈判/复审', '6', 'transactionStage');
INSERT INTO `tbl_dictionaryvalue` VALUES ('39ee5902c59c4655867c35b8336e5aae', 'demand_analysis', '02需求分析', '2', 'transactionStage');
INSERT INTO `tbl_dictionaryvalue` VALUES ('4', '二级部门', '二级部门', '2', 'orgType');
INSERT INTO `tbl_dictionaryvalue` VALUES ('5', '三级部门', '三级部门', '3', 'orgType');
INSERT INTO `tbl_dictionaryvalue` VALUES ('557ab37933fb43529d5131472174a936', 'close_by_compete', '09因竞争丢失关闭', '9', 'transactionStage');
INSERT INTO `tbl_dictionaryvalue` VALUES ('6', 'man', '先生', '1', 'calls');
INSERT INTO `tbl_dictionaryvalue` VALUES ('661bed92823b4f1590566c5a89a4641e', 'sales_call', '推销电话', '2', 'clueSource');
INSERT INTO `tbl_dictionaryvalue` VALUES ('7', 'lady', '女士', '2', 'calls');
INSERT INTO `tbl_dictionaryvalue` VALUES ('7109a01aff5e4ed380dfeed2948dd35b', 'employee_referrals', '员工介绍', '3', 'clueSource');
INSERT INTO `tbl_dictionaryvalue` VALUES ('731c7262223c4a62b09ab155aa3ea9c2', 'lose', '08丢失的线索', '8', 'transactionStage');
INSERT INTO `tbl_dictionaryvalue` VALUES ('8', 'madam', '夫人', '3', 'calls');
INSERT INTO `tbl_dictionaryvalue` VALUES ('8979706cc9274a21b0f734719ed6143b', 'worth_suggest', '03价值建议', '3', 'transactionStage');
INSERT INTO `tbl_dictionaryvalue` VALUES ('9', 'm', '男', '1', 'sex');
INSERT INTO `tbl_dictionaryvalue` VALUES ('ad52569685c9423eb78ae8598898e8fe', 'proposal/offer', '05提案/报价', '5', 'transactionStage');
INSERT INTO `tbl_dictionaryvalue` VALUES ('bb264ed6084f4645893564c0fd70a8d7', 'doctor', '博士', '5', 'calls');
INSERT INTO `tbl_dictionaryvalue` VALUES ('c323df59dcb14ecda3ba6262e65e41bc', 'has_business', '已有业务', '1', 'transType');
INSERT INTO `tbl_dictionaryvalue` VALUES ('c4b846506c334df4b86f61e7d45b199f', 'new_business', '新业务', '2', 'transType');
INSERT INTO `tbl_dictionaryvalue` VALUES ('c71d1a8e636f41939b7d162ad97c3675', 'contact_future', '将来联系', '2', 'clueState');
INSERT INTO `tbl_dictionaryvalue` VALUES ('d9d199c53a0f4579824e0d019b873438', 'advertising', '广告', '1', 'clueSource');
INSERT INTO `tbl_dictionaryvalue` VALUES ('ebbb2a8d7f1a47fabd39edbb1f070efd', 'external_introduction', '外部介绍', '4', 'clueSource');
INSERT INTO `tbl_dictionaryvalue` VALUES ('fe457f7b8fbd4760bcba7d448a77b2cd', 'ensure', '04确定决策者', '4', 'transactionStage');

-- ----------------------------
-- Table structure for tbl_market
-- ----------------------------
DROP TABLE IF EXISTS `tbl_market`;
CREATE TABLE `tbl_market` (
  `name` varchar(30) DEFAULT NULL COMMENT '活动名',
  `id` char(32) NOT NULL,
  `owner` varchar(32) DEFAULT NULL COMMENT '所有者标识',
  `start_date` char(10) DEFAULT '' COMMENT '开始时间',
  `end_date` char(10) DEFAULT '' COMMENT '结束时间',
  `cost` double(10,2) DEFAULT NULL COMMENT '花费',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `creater` varchar(255) DEFAULT NULL COMMENT '创建者标识',
  `createTime` char(19) DEFAULT '',
  `editer` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='市场活动表';

-- ----------------------------
-- Records of tbl_market
-- ----------------------------
INSERT INTO `tbl_market` VALUES ('发传单', '1', '3', '2018-07-11', '', '1.23', '1', '张三', '2018-07-19', '张三', '2018-07-19 19:57:48');
INSERT INTO `tbl_market` VALUES ('ww2', '10', '2', '2018-07-11', '2018-07-11', '30.00', '30', '1111', null, null, null);
INSERT INTO `tbl_market` VALUES ('w1', '11', '2', '2018-07-11', '2018-07-11', null, null, '王五', null, null, null);
INSERT INTO `tbl_market` VALUES ('aaa', '1e07ee04a0174dbca9c14795d0c15d9e', '2', '2018-06-25', '2018-06-29', '200.00', '20000', 'admin', null, null, null);
INSERT INTO `tbl_market` VALUES ('发传单', '2', '2', '2018-06-12', '2018-07-10', null, null, '张三', null, null, null);
INSERT INTO `tbl_market` VALUES ('123', '222', '2', '2018-07-11', '2018-07-11', '20.00', '20', '1111', null, null, null);
INSERT INTO `tbl_market` VALUES ('ss', '3', '2', '2018-07-03', '2018-07-01', null, null, '王五', null, null, null);
INSERT INTO `tbl_market` VALUES ('111522233333', '54f045397b67444fb2a0f42acc26ee34', '9', '2018-07-12', '2018-07-12', '1000.00', '1000', '李方', null, 'xxx', '2018-08-31 21:28:20');
INSERT INTO `tbl_market` VALUES ('ww', '7', '3', '2018-07-11', '2018-07-11', null, null, '时光', null, null, null);
INSERT INTO `tbl_market` VALUES ('威威2', '74b23d8a1dea4d66a446a39b62a8806f', '2', '2018-07-12', '', '225.00', '222', '888', null, '', '2018-07-24 14:47:57');
INSERT INTO `tbl_market` VALUES ('w2', '8', '4', '2018-07-11', '2018-07-11', null, null, 'xxx', null, null, null);
INSERT INTO `tbl_market` VALUES ('456', '888', '6', '2018-07-11', '2018-07-11', '20.00', '20', '1111', '', null, '');
INSERT INTO `tbl_market` VALUES ('121', '999', '6', '2018-07-12', '2018-07-12', '1000.00', '1000', '李方', '', null, '');
INSERT INTO `tbl_market` VALUES ('131', 'a11aa5b855614098ac36cd6698c2eb87', '6', '2018-06-27', '2018-06-29', '200.00', '200', '李方', null, null, null);
INSERT INTO `tbl_market` VALUES ('aas', 'e5a85092ff7848139f3c689c67b984c9', '9', '2018-07-04', '2018-07-21', '500.00', '50', '时光', null, null, null);
INSERT INTO `tbl_market` VALUES ('xxx', 'xxx', '2', '', '', null, null, null, '', null, '');

-- ----------------------------
-- Table structure for tbl_noteactivity
-- ----------------------------
DROP TABLE IF EXISTS `tbl_noteactivity`;
CREATE TABLE `tbl_noteactivity` (
  `id` char(32) NOT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `createBy` varchar(255) DEFAULT NULL COMMENT '创建者标识',
  `createTime` varchar(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL COMMENT '修改者标识',
  `editTime` varchar(19) DEFAULT NULL,
  `editFlag` char(1) DEFAULT NULL COMMENT '修改标识:0未修改,1有修改',
  `p_actId` char(32) DEFAULT NULL COMMENT '活动标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='市场活动备注表';

-- ----------------------------
-- Records of tbl_noteactivity
-- ----------------------------
INSERT INTO `tbl_noteactivity` VALUES ('1532414981822', '123', 'xxx', '2018-7-24 14:49:41', null, null, '0', '74b23d8a1dea4d66a446a39b62a8806f');
INSERT INTO `tbl_noteactivity` VALUES ('1533451847978', '123\n', 'xxx', '2018-08-05 14:50:47', null, null, '0', '888');
INSERT INTO `tbl_noteactivity` VALUES ('1533454334699', '123', 'xxx', '2018-08-05 15:32:14', null, null, '0', '${activity.id}');
INSERT INTO `tbl_noteactivity` VALUES ('1533454656652', '2312', 'xxx', '2018-08-05 15:37:36', null, null, '0', '1');
INSERT INTO `tbl_noteactivity` VALUES ('1533455605396', '111111', 'xxx', '2018-08-05 15:53:25', 'xxx', '2018-8-5 15:53:30', '1', '999');
INSERT INTO `tbl_noteactivity` VALUES ('1535722111628', 'asdasdasd', 'xxx', '2018-08-31 21:28:31', 'xxx', '2018-08-31 21:28:36', '1', '54f045397b67444fb2a0f42acc26ee34');

-- ----------------------------
-- Table structure for tbl_org
-- ----------------------------
DROP TABLE IF EXISTS `tbl_org`;
CREATE TABLE `tbl_org` (
  `id` char(32) NOT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '机构编号',
  `name` varchar(255) DEFAULT NULL COMMENT '机构名称',
  `orgType` varchar(255) DEFAULT NULL COMMENT '机构类型',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `manager` varchar(255) DEFAULT NULL COMMENT '负责人',
  `createBy` varchar(255) DEFAULT NULL COMMENT '创建者姓名',
  `editTime` char(19) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `pId` varchar(255) DEFAULT NULL COMMENT '父机构标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构表';

-- ----------------------------
-- Records of tbl_org
-- ----------------------------
INSERT INTO `tbl_org` VALUES ('1', '1001', 'xx公司', '二级部门', '研发二部李四', '李四', 'xxx', null, '2018-08-12 19:52:34', null, '0');
INSERT INTO `tbl_org` VALUES ('1e1b7f32bc8a4f1bb2074c049070839e', 'b', 'b', '二级部门', null, null, 'xxx', '2018-08-13 21:33:35', '2018-08-13 21:30:21', 'xxx', '3');
INSERT INTO `tbl_org` VALUES ('2', '1002', '研发a部', '一级部门', '研发一部李四', '李四', '没有登录，测试用例', '2018-08-13 22:39:09', '2018-08-12 19:52:36', 'xxx', '1');
INSERT INTO `tbl_org` VALUES ('3', '1001', '研发2部', '一级部门', '研发一部李四', '李四', 'xxx', '', '2018-08-12 19:52:35', '', '1');
INSERT INTO `tbl_org` VALUES ('4', '1001', '研发3部', '一级部门', '研发一部李四', '李四', 'xxx', '', '2018-08-12 19:52:37', '', '1');
INSERT INTO `tbl_org` VALUES ('5', '1009', '研发4部', '二级部门', 'aa', 'a', 'xxx', '2018-08-13 11:04:47', '2018-08-12 19:52:38', 'xxx', '4');
INSERT INTO `tbl_org` VALUES ('6', 'a', 'a2', '二级部门', 'a', 'a', 'xxx', null, '2018-08-13 13:06:47', null, '4');

-- ----------------------------
-- Table structure for tbl_permission
-- ----------------------------
DROP TABLE IF EXISTS `tbl_permission`;
CREATE TABLE `tbl_permission` (
  `id` char(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `modalURL` varchar(255) DEFAULT NULL COMMENT '模块url',
  `operateURL` varchar(255) DEFAULT NULL COMMENT '操作url',
  `sortNo` varchar(255) DEFAULT NULL COMMENT '排序号',
  `pid` char(32) DEFAULT NULL COMMENT '父许可标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限许可表';

-- ----------------------------
-- Records of tbl_permission
-- ----------------------------
INSERT INTO `tbl_permission` VALUES ('1', 'CRM', 'workbench/activity/index.jsp', '/workbench/activity/add.do', '0', '0');
INSERT INTO `tbl_permission` VALUES ('2', '线索', 'workbench.clue/index.jsp', null, '1', '0');
INSERT INTO `tbl_permission` VALUES ('2b06d7ad73d14b38aef48369e15d6491', '创建线索', 'workbench.clue/index.jsp', 'workbench.clue/save.do', '1', '2');
INSERT INTO `tbl_permission` VALUES ('74c904abe31c4845bb203cd8891dd965', '创建市场活动 ', 'workbench/activity/index.jsp', '/workbench/activity/add.do', '1', '1');
INSERT INTO `tbl_permission` VALUES ('e7f4df9b77264b68853cd5a80211d16f', 'a', '', 'a', '2', '02f45d1f738e41d68e03a5b688a68698');
INSERT INTO `tbl_permission` VALUES ('f2aac639f23f4be38f3832a54ec22638', '修改市场活动', 'workbench/activity/index.jsp', '/workbench/activity/update.do', '2', '1');

-- ----------------------------
-- Table structure for tbl_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role`;
CREATE TABLE `tbl_role` (
  `no` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `name` varchar(255) DEFAULT '' COMMENT '角色名称',
  `description` varchar(255) DEFAULT '' COMMENT '描述',
  `id` char(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of tbl_role
-- ----------------------------
INSERT INTO `tbl_role` VALUES ('001', '管理员', '管理员具有最高权限1', '1');
INSERT INTO `tbl_role` VALUES ('002', '销售总监', null, '2');
INSERT INTO `tbl_role` VALUES ('003', '市场总监', null, '3');
INSERT INTO `tbl_role` VALUES ('004', '0002', '0002', '73676e10360443a5a0068f749a77d94d');
INSERT INTO `tbl_role` VALUES ('005', '003', '003', '9459a276b08a4e72981e669113f874de');

-- ----------------------------
-- Table structure for tbl_role_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role_permission_relation`;
CREATE TABLE `tbl_role_permission_relation` (
  `id` char(32) NOT NULL,
  `roleId` char(32) DEFAULT NULL COMMENT '角色标识',
  `permissionId` char(32) DEFAULT NULL COMMENT '许可标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与许可关系表';

-- ----------------------------
-- Records of tbl_role_permission_relation
-- ----------------------------
INSERT INTO `tbl_role_permission_relation` VALUES ('1', '1', '1');
INSERT INTO `tbl_role_permission_relation` VALUES ('2', '1', '2');

-- ----------------------------
-- Table structure for tbl_trans
-- ----------------------------
DROP TABLE IF EXISTS `tbl_trans`;
CREATE TABLE `tbl_trans` (
  `id` char(32) NOT NULL,
  `owner` char(32) DEFAULT NULL COMMENT '所有者标识',
  `money` varchar(255) DEFAULT NULL COMMENT '交易金额',
  `name` varchar(255) DEFAULT NULL COMMENT '交易名称',
  `expectedDate` char(10) DEFAULT NULL COMMENT '预计成交日期',
  `customerId` char(32) DEFAULT NULL COMMENT '客户标识',
  `stage` varchar(255) DEFAULT NULL COMMENT '交易所处阶段',
  `type` varchar(255) DEFAULT NULL COMMENT '交易类型',
  `source` varchar(255) DEFAULT NULL COMMENT '交易来源',
  `activityId` varchar(255) DEFAULT NULL COMMENT '交易关联的市场活动标识',
  `contactId` varchar(255) DEFAULT NULL,
  `createBy` char(32) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `editBy` char(32) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `contactSummary` varchar(255) DEFAULT NULL,
  `nextContactTime` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易表';

-- ----------------------------
-- Records of tbl_trans
-- ----------------------------
INSERT INTO `tbl_trans` VALUES ('0632434182c14e08bbfd26d03dbdc2a3', '5', '2300', '123', '2018-06-27', '82a76255fe3c4eed9f332be9448e1311', '03价值建议', '已有业务', '推销电话', '', '2', '没有登录，测试用例', '2018-07-30 22:19:38', '2018-08-14 18:12:42', 'xxx', '00s', 'ss', '1899-12-31');
INSERT INTO `tbl_trans` VALUES ('497651d43e4548bdbb6afd0fde9586b0', null, null, 'bbb', '2018-08-07', '2', '04确定决策', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tbl_trans` VALUES ('c2e1136c3e6744169278e3a3813bd886', '9', '123', 'aaa', '2018-08-07', '82a76255fe3c4eed9f332be9448e1311', '04确定决策者', '新业务', '外部介绍', null, null, 'xxx', '2018-08-01 16:37:08', '2018-08-01 16:37:25', 'xxx', null, null, null);

-- ----------------------------
-- Table structure for tbl_trans_history
-- ----------------------------
DROP TABLE IF EXISTS `tbl_trans_history`;
CREATE TABLE `tbl_trans_history` (
  `id` char(32) NOT NULL,
  `stage` varchar(255) DEFAULT NULL COMMENT '交易所处阶段',
  `money` varchar(255) DEFAULT NULL COMMENT '交易金额',
  `expectedDate` char(19) DEFAULT NULL COMMENT '预计成交日期',
  `createBy` char(32) DEFAULT NULL COMMENT '创建者姓名',
  `createTime` char(19) DEFAULT NULL,
  `transId` char(32) DEFAULT NULL COMMENT '交易标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易历史纪录表';

-- ----------------------------
-- Records of tbl_trans_history
-- ----------------------------
INSERT INTO `tbl_trans_history` VALUES ('0f67f5656b8247338b36ad4683ab97bd', '02需求分析', '2300', '2018-06-27', '没有登录，测试用例', '2018-07-30 22:19:38', '0632434182c14e08bbfd26d03dbdc2a3');
INSERT INTO `tbl_trans_history` VALUES ('198462f27dce4569a06ed7385d8953ec', '05提案/报价', '5,000', '2018-06-05', '没有登录，测试用例', '2018-07-30 16:20:46', '1');
INSERT INTO `tbl_trans_history` VALUES ('1e5beac285ef4b199a41abcccfd62db8', '09因竞争丢失关闭', '5,000', '2018-07-27', '没有登录，测试用例', '2018-07-30 16:30:26', '497651d43e4548bdbb6afd0fde9586b0');
INSERT INTO `tbl_trans_history` VALUES ('2a2a5b5684904dffad917865caaa35e8', '03价值建议', '5,000', '2018-07-27', '没有登录，测试用例', '2018-07-30 15:52:14', '497651d43e4548bdbb6afd0fde9586b0');
INSERT INTO `tbl_trans_history` VALUES ('4b23fc801c2340df9151a3fcbebf254f', '05提案/报价', '5,000', '2018-06-05', '没有登录，测试用例', '2018-07-30 16:20:33', '1');
INSERT INTO `tbl_trans_history` VALUES ('533c6dc0bd10409bb014ff5a21dc304e', '需求分析', '12313', '2018-07-27', '没有登录，测试用例', '2018-07-30 14:39:19', '497651d43e4548bdbb6afd0fde9586b0');
INSERT INTO `tbl_trans_history` VALUES ('60f53bb73c7947a3883d0d4d6d4c1ce7', '05提案/报价', '5,000', '2018-06-05', '没有登录，测试用例', '2018-07-30 15:56:48', '1');
INSERT INTO `tbl_trans_history` VALUES ('8efb25a2d4dd44d4bdeabae18bc7577c', '04确定决策者', '123', '2018-08-07', 'xxx', '2018-08-01 16:37:25', 'c2e1136c3e6744169278e3a3813bd886');
INSERT INTO `tbl_trans_history` VALUES ('8f1edec4ef65475896dd27b88de968aa', '04确定决策者', '5,000', '2018-07-27', '没有登录，测试用例', '2018-07-30 16:20:04', '497651d43e4548bdbb6afd0fde9586b0');
INSERT INTO `tbl_trans_history` VALUES ('a810369ffee84ba18bc199e6cde32369', '08丢失的线索', '5,000', '2018-07-27', '没有登录，测试用例', '2018-07-30 16:19:56', '497651d43e4548bdbb6afd0fde9586b0');
INSERT INTO `tbl_trans_history` VALUES ('b7c8869f24ae4643a15e5a5db301f68f', '04确定决策者', '5,000', '2018-07-27', '没有登录，测试用例', '2018-07-30 16:29:07', '497651d43e4548bdbb6afd0fde9586b0');
INSERT INTO `tbl_trans_history` VALUES ('cf615ff5135b498786bfdd9ec281de4c', '04确定决策者', '5,000', '2018-06-05', '没有登录，测试用例', '2018-07-30 15:54:57', '1');
INSERT INTO `tbl_trans_history` VALUES ('da64dc2adba94e8eab9dcd89765a085e', '05提案/报价', '5,000', '2018-06-05', '没有登录，测试用例', '2018-07-30 15:55:20', '1');
INSERT INTO `tbl_trans_history` VALUES ('ecb31e0fe4124f12a06ee8d70a3515a7', '04确定决策者', '5,000', '2018-07-27', '没有登录，测试用例', '2018-07-30 16:20:58', '497651d43e4548bdbb6afd0fde9586b0');
INSERT INTO `tbl_trans_history` VALUES ('f1da5b25e9124372802a915f8d424715', '02需求分析', '123', '2018-08-07', 'xxx', '2018-08-01 16:37:08', 'c2e1136c3e6744169278e3a3813bd886');
INSERT INTO `tbl_trans_history` VALUES ('f3d4903b6cf1400186ed8ab02793b828', '03价值建议', '2300', '2018-06-27', 'xxx', '2018-08-14 18:12:42', '0632434182c14e08bbfd26d03dbdc2a3');
INSERT INTO `tbl_trans_history` VALUES ('ffea6f239fae40f2b081e4605788557c', '04确定决策者', '5,000', '2018-06-05', '没有登录，测试用例', '2018-07-30 15:55:55', '1');

-- ----------------------------
-- Table structure for tbl_trans_remark
-- ----------------------------
DROP TABLE IF EXISTS `tbl_trans_remark`;
CREATE TABLE `tbl_trans_remark` (
  `id` char(32) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `transId` char(32) DEFAULT NULL,
  `createBy` char(32) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` char(32) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `editFlag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易备注表';

-- ----------------------------
-- Records of tbl_trans_remark
-- ----------------------------
INSERT INTO `tbl_trans_remark` VALUES ('1bc8e5e738624188abb614e5cf0755a5', '132', '497651d43e4548bdbb6afd0fde9586b0', '没有登录，测试用例', '2018-07-30 16:22:45', null, null, '0');
INSERT INTO `tbl_trans_remark` VALUES ('84e1d89612f24f55b4a9fc3179df84cd', '123', '1', 'xxx', '2018-08-02 12:09:36', null, null, '0');
INSERT INTO `tbl_trans_remark` VALUES ('dd7681992fbb4e22b0b30a4085c6d2a5', '123', '497651d43e4548bdbb6afd0fde9586b0', 'xxx', '2018-08-02 12:13:26', null, null, '0');

-- ----------------------------
-- Table structure for tbl_usermanage
-- ----------------------------
DROP TABLE IF EXISTS `tbl_usermanage`;
CREATE TABLE `tbl_usermanage` (
  `id` char(32) NOT NULL,
  `account` varchar(255) DEFAULT NULL COMMENT '用户账号',
  `username` varchar(255) DEFAULT NULL,
  `deptno` char(32) DEFAULT NULL COMMENT '锁定状态',
  `email` varchar(255) DEFAULT NULL,
  `invalid_time` char(19) DEFAULT NULL COMMENT '失效时间',
  `permit_ip` varchar(255) DEFAULT NULL COMMENT '允许ip',
  `lockState` varchar(2) DEFAULT NULL COMMENT '锁定状态',
  `createBy` varchar(255) DEFAULT NULL COMMENT '创建者姓名',
  `password` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(10) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of tbl_usermanage
-- ----------------------------
INSERT INTO `tbl_usermanage` VALUES ('1', 'zhangsan', '张三', 'M001', 'zhangsan@bjpowernode.com', '2019-01-14 10:10:10', '', '启用', 'xxx', '123', '2017-02-10 10:10:10', 'xxx', '2017-02-10 20:10:10');
INSERT INTO `tbl_usermanage` VALUES ('3', 'wangwu', '王五', 'M001', 'aaaa@xxx.com', '2019-02-15 10:10:10', '192.168.100.2', '启用', '', '202cb962ac59075b964b07152d234b70', '2017-02-10 10:10:10', 'xxx', '2018-08-15 18:15:32');
INSERT INTO `tbl_usermanage` VALUES ('4', 'admin', '管理员', '40', 'andaid@163.com', '2019-10-31', '', '启用', 'xxx', '202cb962ac59075b964b07152d234b70', '2018-07-16 20:10:04', 'xxx', '2018-07-25 21:23:14');
INSERT INTO `tbl_usermanage` VALUES ('5', 'zhaolei', '赵雷', 'M001', 'bbb@bjpowernode.com', '2017-02-14 10:10:10', '127.0.0.1,192.168.100.2', '锁定', 'xxx', '123', '2017-02-10 10:10:10', 'xxx', '2017-02-10 20:10:10');
INSERT INTO `tbl_usermanage` VALUES ('6', 'lifang', '李方', 'M003', 'vvv@bjpowernode.com', '2019-02-24 10:10:10', '127.0.0.1,192.168.100.2', '启用', 'xxx', '123', '2017-02-10 10:10:10', 'xxx', '2017-02-10 20:10:10');
INSERT INTO `tbl_usermanage` VALUES ('7', 'shiguang', '时光', 'M001', 'ccc@bjpowernode.com', '2017-02-16 10:10:10', '127.0.0.1,192.168.100.2', '启用', 'xxx', '123', '2017-02-10 10:10:10', 'xxx', '');
INSERT INTO `tbl_usermanage` VALUES ('9', 'xxx', 'xxx', 'M001', 'xxx@test.com', '2018-09-27 00:00:00', '', '启用', 'xxx', '202cb962ac59075b964b07152d234b70', '2018-07-15 12:06:28', '', '2018-07-17 17:14:07');

-- ----------------------------
-- Table structure for tbl_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_role_relation`;
CREATE TABLE `tbl_user_role_relation` (
  `id` char(32) NOT NULL,
  `userId` char(32) DEFAULT NULL,
  `roleId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色关系表';

-- ----------------------------
-- Records of tbl_user_role_relation
-- ----------------------------
