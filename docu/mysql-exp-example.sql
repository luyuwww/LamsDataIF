/*
Navicat MySQL Data Transfer

Source Server         : mysql-aliyun
Source Server Version : 50616
Source Host           : beijing2008.mysql.rds.aliyuncs.com:3308
Source Database       : lams

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2019-06-26 23:42:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for f_p_z1323
-- ----------------------------
DROP TABLE IF EXISTS `f_p_z1323`;
CREATE TABLE `f_p_z1323` (
  `DID` int(11) NOT NULL,
  `FIELDNAME` varchar(32) NOT NULL,
  `CHNAME` varchar(64) NOT NULL,
  `FIELDTYPE` int(11) NOT NULL,
  `LENGTH` int(11) DEFAULT NULL,
  `NOTNULL` int(11) DEFAULT NULL,
  `NOTDUP` int(11) DEFAULT NULL,
  `NOTINWEB` int(11) DEFAULT NULL,
  `FCATTR` int(11) DEFAULT NULL,
  `FMATTR` int(11) DEFAULT NULL,
  `READONLYCOLS` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`DID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of f_p_z1323
-- ----------------------------
INSERT INTO `f_p_z1323` VALUES ('1', 'DID', 'DID', '3', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1323` VALUES ('2', 'DALXCHNAME', 'DALXCHNAME', '1', '50', '1', '0', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1323` VALUES ('3', 'LIBCODE', 'LIBCODE', '3', '49', '1', '0', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1323` VALUES ('4', 'DBURL', 'DBURL', '1', '65', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1323` VALUES ('5', 'DBNAME', 'DBNAME', '1', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1323` VALUES ('6', 'DBTYPE', 'DBTYPE', '1', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1323` VALUES ('7', 'USERNAME', 'USERNAME', '1', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1323` VALUES ('8', 'PASSWORD', 'PASSWORD', '1', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1323` VALUES ('9', 'MAXLEVEL', 'MAXLEVEL', '1', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1323` VALUES ('10', 'BZ', 'BZ', '1', '512', '0', '0', '0', '1', '0', '0100000111000000');

-- ----------------------------
-- Table structure for f_p_z1324
-- ----------------------------
DROP TABLE IF EXISTS `f_p_z1324`;
CREATE TABLE `f_p_z1324` (
  `DID` int(11) NOT NULL,
  `FIELDNAME` varchar(32) NOT NULL,
  `CHNAME` varchar(64) NOT NULL,
  `FIELDTYPE` int(11) NOT NULL,
  `LENGTH` int(11) DEFAULT NULL,
  `NOTNULL` int(11) DEFAULT NULL,
  `NOTDUP` int(11) DEFAULT NULL,
  `NOTINWEB` int(11) DEFAULT NULL,
  `FCATTR` int(11) DEFAULT NULL,
  `FMATTR` int(11) DEFAULT NULL,
  `READONLYCOLS` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`DID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of f_p_z1324
-- ----------------------------
INSERT INTO `f_p_z1324` VALUES ('1', 'PID', 'PID', '3', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('2', 'DID', 'DID', '3', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('3', 'STBNAME', 'STBNAME', '1', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('4', 'TTBNAME', 'TTBNAME', '1', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('5', 'MTBNAME', 'MTBNAME', '1', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('6', 'TSQL', 'TSQL', '1', '1000', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('7', 'PIDSQL', 'PIDSQL', '1', '1000', '0', '0', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('8', 'CALLBACKSQL', 'CALLBACKSQL', '1', '1000', '0', '0', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('9', 'BZ', 'BZ', '1', '100', '0', '0', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('10', 'DEFAULTFIELD', 'DEFAULTFIELD', '1', '1000', '0', '0', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('11', 'DEFAULTVALUE', 'DEFAULTVALUE', '1', '1000', '0', '0', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1324` VALUES ('12', 'UPDATESQL', 'UPDATESQL', '1', '1000', '0', '0', '0', '1', '0', '0100000111000000');

-- ----------------------------
-- Table structure for f_p_z1325
-- ----------------------------
DROP TABLE IF EXISTS `f_p_z1325`;
CREATE TABLE `f_p_z1325` (
  `DID` int(11) NOT NULL,
  `FIELDNAME` varchar(32) NOT NULL,
  `CHNAME` varchar(64) NOT NULL,
  `FIELDTYPE` int(11) NOT NULL,
  `LENGTH` int(11) DEFAULT NULL,
  `NOTNULL` int(11) DEFAULT NULL,
  `NOTDUP` int(11) DEFAULT NULL,
  `NOTINWEB` int(11) DEFAULT NULL,
  `FCATTR` int(11) DEFAULT NULL,
  `FMATTR` int(11) DEFAULT NULL,
  `READONLYCOLS` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`DID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of f_p_z1325
-- ----------------------------
INSERT INTO `f_p_z1325` VALUES ('1', 'PPID', 'PPID', '3', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1325` VALUES ('2', 'PID', 'PID', '3', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1325` VALUES ('3', 'DID', 'DID', '3', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1325` VALUES ('4', 'SFIELD', 'SFIELD', '1', '64', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1325` VALUES ('5', 'TFIELD', 'TFIELD', '1', '64', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1325` VALUES ('6', 'DEFAULTVALUE', 'DEFAULTVALUE', '1', '64', '0', '0', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1325` VALUES ('7', 'BZ', 'BZ', '1', '1000', '0', '0', '0', '1', '0', '0100000111000000');

-- ----------------------------
-- Table structure for f_p_z1326
-- ----------------------------
DROP TABLE IF EXISTS `f_p_z1326`;
CREATE TABLE `f_p_z1326` (
  `DID` int(11) NOT NULL,
  `FIELDNAME` varchar(32) NOT NULL,
  `CHNAME` varchar(64) NOT NULL,
  `FIELDTYPE` int(11) NOT NULL,
  `LENGTH` int(11) DEFAULT NULL,
  `NOTNULL` int(11) DEFAULT NULL,
  `NOTDUP` int(11) DEFAULT NULL,
  `NOTINWEB` int(11) DEFAULT NULL,
  `FCATTR` int(11) DEFAULT NULL,
  `FMATTR` int(11) DEFAULT NULL,
  `READONLYCOLS` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`DID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of f_p_z1326
-- ----------------------------
INSERT INTO `f_p_z1326` VALUES ('1', 'PPID', 'PPID', '3', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1326` VALUES ('2', 'PID', 'PID', '3', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1326` VALUES ('3', 'DID', 'DID', '3', '50', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1326` VALUES ('4', 'SFIELD', 'SFIELD', '1', '64', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1326` VALUES ('5', 'TFIELD', 'TFIELD', '1', '64', '1', '1', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1326` VALUES ('6', 'DEFAULTVALUE', 'DEFAULTVALUE', '1', '64', '0', '0', '0', '1', '0', '0100000111000000');
INSERT INTO `f_p_z1326` VALUES ('7', 'BZ', 'BZ', '1', '1000', '0', '0', '0', '1', '0', '0100000111000000');

-- ----------------------------
-- Table structure for p_z1323
-- ----------------------------
DROP TABLE IF EXISTS `p_z1323`;
CREATE TABLE `p_z1323` (
  `DID` int(11) DEFAULT NULL,
  `DALXCHNAME` varchar(50) NOT NULL,
  `LIBCODE` int(11) NOT NULL,
  `DBURL` varchar(65) NOT NULL,
  `DBNAME` varchar(50) DEFAULT NULL,
  `DBTYPE` varchar(50) DEFAULT NULL,
  `USERNAME` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(50) DEFAULT NULL,
  `MAXLEVEL` varchar(50) NOT NULL,
  `BZ` varchar(512) DEFAULT NULL,
  UNIQUE KEY `P_Z1323_F9_uniqueName` (`MAXLEVEL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_z1323
-- ----------------------------
INSERT INTO `p_z1323` VALUES ('1', '输电工程', '3', '127.0.0.1:1433', 'GZED_THAMSXP', 'MSSQL', 'sa', 'ams2000', 'PRJ', null);

-- ----------------------------
-- Table structure for p_z1324
-- ----------------------------
DROP TABLE IF EXISTS `p_z1324`;
CREATE TABLE `p_z1324` (
  `PID` int(11) NOT NULL,
  `DID` int(11) NOT NULL,
  `STBNAME` varchar(50) NOT NULL,
  `TTBNAME` varchar(50) NOT NULL,
  `MTBNAME` varchar(50) NOT NULL,
  `TSQL` varchar(1000) NOT NULL,
  `PIDSQL` varchar(1000) DEFAULT NULL,
  `CALLBACKSQL` varchar(1000) DEFAULT NULL,
  `BZ` varchar(100) DEFAULT NULL,
  `DEFAULTFIELD` varchar(1000) DEFAULT NULL,
  `DEFAULTVALUE` varchar(1000) DEFAULT NULL,
  `UPDATESQL` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_z1324
-- ----------------------------
INSERT INTO `p_z1324` VALUES ('1', '1', 'LIBPRJ5', 'D_PRJ3', '中间表字段关系映射_D_PRJ3', 'SELECT CREATETIME,PRJCODE,PRJNAME,XMFZR,GCLB,LXRQ,F1,F2  FROM LIBPRJ5 WHERE KYWCRQ<>1 OR KYWCRQ IS NULL', '', '', '', 'STATUS,ATTR,ATTREX,BMID,QZH', '0,0,0,\'52\',\'52\'', 'UPDATE LIBPRJ5 SET KYWCRQ=1 WHERE PRJCODE=\'--$PRJCODE$--\'');
INSERT INTO `p_z1324` VALUES ('1', '2', 'LIBVOL5', 'D_VOL5', '中间表字段关系映射_D_VOL3', 'SELECT AUTHOR,CREATETIME,EDITOR,EDITTIME,BMDM,PRJCODE,KEYWORD,TITLE,FLH,AJH,BGQX,MJ,GCLB,ZY,BZZ,RKSJ,MLYS,TYZS,SJDW,GCDZ,XMMC,SJJD,BZSJ,DJSJ,TZZS,CTZS,BZDW,DAWZ,CBSJ,XHBZ,DWDH from LIBVOL5 WHERE F3<>\' OR F3 IS NULL', 'SELECT DID FROM D_PRJ3 WHERE PRJCODE=\'--$XMDH$--\'', null, null, 'STATUS,ATTR,ATTREX,BMID,QZH', '0,0,0,\'52\',\'52\'', 'UPDATE LIBVOL5 SET F3=\'1\' WHERE KEYWORD=\'--$KEYWORD$--\'');

-- ----------------------------
-- Table structure for p_z1325
-- ----------------------------
DROP TABLE IF EXISTS `p_z1325`;
CREATE TABLE `p_z1325` (
  `PPID` int(11) NOT NULL,
  `PID` int(11) NOT NULL,
  `DID` int(11) NOT NULL,
  `SFIELD` varchar(64) NOT NULL,
  `TFIELD` varchar(64) NOT NULL,
  `DEFAULTVALUE` varchar(64) DEFAULT NULL,
  `BZ` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_z1325
-- ----------------------------
INSERT INTO `p_z1325` VALUES ('1', '1', '1', 'CREATETIME', 'CREATETIME', 'GETDATE()', '符合数据库类型的时间函数');
INSERT INTO `p_z1325` VALUES ('1', '1', '2', 'PRJCODE', 'PRJCODE', null, null);
INSERT INTO `p_z1325` VALUES ('1', '1', '3', 'PRJNAME', 'PRJNAME', null, null);
INSERT INTO `p_z1325` VALUES ('1', '1', '4', 'XMFZR', 'XMFZR', null, null);
INSERT INTO `p_z1325` VALUES ('1', '1', '5', 'GCLB', 'GCLB', null, null);
INSERT INTO `p_z1325` VALUES ('1', '1', '6', 'LXRQ', 'LXRQ', null, null);
INSERT INTO `p_z1325` VALUES ('1', '1', '7', 'F1', 'DYDJ', '11', null);
INSERT INTO `p_z1325` VALUES ('1', '1', '8', 'F2', 'SJJD', '默认等级', null);

-- ----------------------------
-- Table structure for p_z1326
-- ----------------------------
DROP TABLE IF EXISTS `p_z1326`;
CREATE TABLE `p_z1326` (
  `PPID` int(11) NOT NULL,
  `PID` int(11) NOT NULL,
  `DID` int(11) NOT NULL,
  `SFIELD` varchar(64) NOT NULL,
  `TFIELD` varchar(64) NOT NULL,
  `DEFAULTVALUE` varchar(64) DEFAULT NULL,
  `BZ` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_z1326
-- ----------------------------
INSERT INTO `p_z1326` VALUES ('1', '1', '1', 'AUTHOR', 'KEYWORD', null, null);
INSERT INTO `p_z1326` VALUES ('1', '2', '2', 'CREATETIME', 'CREATETIME', null, null);
INSERT INTO `p_z1326` VALUES ('1', '3', '3', 'EDITOR', 'EDITOR', null, null);
INSERT INTO `p_z1326` VALUES ('1', '4', '4', 'EDITTIME', 'EDITTIME', null, null);
INSERT INTO `p_z1326` VALUES ('1', '5', '5', 'BMDM', 'BMDM', null, null);
INSERT INTO `p_z1326` VALUES ('1', '6', '6', 'PRJCODE', 'PRJCODE', null, null);
INSERT INTO `p_z1326` VALUES ('1', '7', '7', 'KEYWORD', 'KEYWORD', null, null);
INSERT INTO `p_z1326` VALUES ('1', '8', '8', 'TITLE', 'TITLE', null, null);
INSERT INTO `p_z1326` VALUES ('1', '9', '9', 'FLH', 'FLH', null, null);
INSERT INTO `p_z1326` VALUES ('1', '10', '10', 'AJH', 'AJH', null, null);
INSERT INTO `p_z1326` VALUES ('1', '11', '11', 'BGQX', 'BGQX', null, null);
INSERT INTO `p_z1326` VALUES ('1', '12', '12', 'MJ', 'MJ', null, null);
INSERT INTO `p_z1326` VALUES ('1', '13', '13', 'GCLB', 'GCLB', null, null);
INSERT INTO `p_z1326` VALUES ('1', '14', '14', 'ZY', 'ZY', null, null);
INSERT INTO `p_z1326` VALUES ('1', '15', '15', 'BZZ', 'BZZ', null, null);
INSERT INTO `p_z1326` VALUES ('1', '16', '16', 'RKSJ', 'RKSJ', null, null);
INSERT INTO `p_z1326` VALUES ('1', '17', '17', 'MLYS', 'MLYS', null, null);
INSERT INTO `p_z1326` VALUES ('1', '18', '18', 'TYZS', 'TYZS', null, null);
INSERT INTO `p_z1326` VALUES ('1', '19', '19', 'SJDW', 'SJDW', null, null);
INSERT INTO `p_z1326` VALUES ('1', '20', '20', 'GCDZ', 'GCDZ', null, null);
INSERT INTO `p_z1326` VALUES ('1', '21', '21', 'XMMC', 'XMMC', null, null);
INSERT INTO `p_z1326` VALUES ('1', '22', '22', 'SJJD', 'SJJD', null, null);
INSERT INTO `p_z1326` VALUES ('1', '23', '23', 'BZSJ', 'BZSJ', null, null);
INSERT INTO `p_z1326` VALUES ('1', '24', '24', 'DJSJ', 'DJSJ', null, null);
INSERT INTO `p_z1326` VALUES ('1', '25', '25', 'TZZS', 'TZZS', null, null);
INSERT INTO `p_z1326` VALUES ('1', '26', '26', 'CTZS', 'CTZS', null, null);
INSERT INTO `p_z1326` VALUES ('1', '27', '27', 'BZDW', 'BZDW', null, null);
INSERT INTO `p_z1326` VALUES ('1', '28', '28', 'DAWZ', 'DAWZ', null, null);
INSERT INTO `p_z1326` VALUES ('1', '29', '29', 'CBSJ', 'CBSJ', null, null);
INSERT INTO `p_z1326` VALUES ('1', '30', '30', 'XHBZ', 'XHBZ', null, null);
INSERT INTO `p_z1326` VALUES ('1', '31', '31', 'DWDH', 'DWDH', null, null);
