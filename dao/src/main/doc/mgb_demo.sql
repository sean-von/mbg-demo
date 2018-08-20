-- 创建示例 DB
create database mgb_demo;

-- 示例表结构，一个通用操作日志表
CREATE TABLE `opt_log` (
  `id` bigint(20) NOT NULL COMMENT '物理主键，Long型',
  `item` bigint(20) NOT NULL DEFAULT '0' COMMENT '功能点',
  `opt_type` int(11) NOT NULL COMMENT '操作类别 (1.增加 2.修改 3.删除)',
  `old_value` varchar(1000) NOT NULL DEFAULT '' COMMENT '操作前值',
  `new_value` varchar(1000) NOT NULL DEFAULT '' COMMENT '操作后值',
  `keywords` varchar(1000) NOT NULL DEFAULT '' COMMENT '关键词列表',
  `ip` varchar(50) NOT NULL DEFAULT '' COMMENT '操作者IP',
  `is_delete` int(4) NOT NULL DEFAULT '0' COMMENT '软删除标识（0.未删除 1.已删除）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `opt_log_idx_item` (`item`),
  KEY `opt_log_idx_create_time` (`create_time`),
  FULLTEXT KEY `opt_log_idx_keywords` (`keywords`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='实体表，操作记录';