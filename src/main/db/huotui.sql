CREATE TABLE `entrustment` (
  `id` bigint(20) NOT NULL COMMENT 'ID，委托流水号',
  `user_id` bigint(20) NOT NULL COMMENT '委托人ID',
  `apply_type` int(4) NOT NULL COMMENT '委托类别（0.委买 1.委卖）',
  `deal_type` int(4) NOT NULL DEFAULT '0' COMMENT '交易类别(0.市价, 1.限价)',
  `price` bigint(20) NOT NULL DEFAULT '0' COMMENT '委托交易价格',
  `entrust_amount` int(10) NOT NULL DEFAULT '0' COMMENT '委托交易量',
  `deal_amount` int(10) NOT NULL DEFAULT '0' COMMENT '成交量',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '委托状态（0.交易中 1.交易完成 2.交易取消）',
  `is_delete` int(4) NOT NULL DEFAULT '0' COMMENT '删除标记（0.未删除 1.已删除）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `Index_entrustment_user_id` (`user_id`),
  KEY `Index_entrustment_price` (`price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='火焰挂单表'