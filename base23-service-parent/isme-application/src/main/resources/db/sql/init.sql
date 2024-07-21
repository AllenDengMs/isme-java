-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE IF NOT EXISTS `t_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` bigint DEFAULT 0,
  `update_time` bigint DEFAULT 0,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uidx_user_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 1700295539, 1700295539, 'admin', '$2a$10$FsAafxTTVVGXfIkJqvaiV.1vPfq4V9HW298McPldJgO829PR52a56', 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE IF NOT EXISTS `t_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` bigint DEFAULT 0,
  `update_time` bigint DEFAULT 0,
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uidx_role_code`(`code`) USING BTREE,
  UNIQUE INDEX `uidx_role_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 1700295539, 1700295539, 'SUPER_ADMIN', '超级管理员', 1);
INSERT INTO `t_role` VALUES (2, 1700295539, 1700295539, 'ROLE_QA', '质检员', 1);

-- ----------------------------
-- Table structure for menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_menu_permission`;
CREATE TABLE IF NOT EXISTS `t_menu_permission`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `create_time` bigint DEFAULT 0,
    `update_time` bigint DEFAULT 0,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `parent_id` bigint NULL DEFAULT 0,
    `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `layout` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `keep_alive` tinyint(1) NULL DEFAULT false,
    `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    can_show tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否展示在页面菜单',
    `enable` tinyint(1) NOT NULL DEFAULT 1,
    sort_order int(11) NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uidx_menu_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_permission
-- ----------------------------
INSERT INTO `t_menu_permission` VALUES (1, 1700295539, 1700295539, '资源管理', 'Resource_Mgt', 'MENU', 2, '/pms/resource', NULL, 'i-fe:list', '/src/views/pms/resource/index.vue', NULL, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO `t_menu_permission` VALUES (2, 1700295539, 1700295539, '系统管理', 'SysMgt', 'MENU', NULL, NULL, NULL, 'i-fe:grid', NULL, NULL, NULL, NULL, NULL, 1, 1, 2);
INSERT INTO `t_menu_permission` VALUES (3, 1700295539, 1700295539, '角色管理', 'RoleMgt', 'MENU', 2, '/pms/role', NULL, 'i-fe:user-check', '/src/views/pms/role/index.vue', NULL, NULL, NULL, NULL, 1, 1, 2);
INSERT INTO `t_menu_permission` VALUES (4, 1700295539, 1700295539, '用户管理', 'UserMgt', 'MENU', 2, '/pms/user', NULL, 'i-fe:user', '/src/views/pms/user/index.vue', NULL, 1, NULL, NULL, 1, 1, 3);
INSERT INTO `t_menu_permission` VALUES (5, 1700295539, 1700295539, '分配用户', 'RoleUser', 'MENU', 3, '/pms/role/user/:roleId', NULL, 'i-fe:user-plus', '/src/views/pms/role/role-user.vue', 'full', NULL, NULL, NULL, 0, 1, 1);
INSERT INTO `t_menu_permission` VALUES (6, 1700295539, 1700295539, '业务示例', 'Demo', 'MENU', NULL, NULL, NULL, 'i-fe:grid', NULL, NULL, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO `t_menu_permission` VALUES (7, 1700295539, 1700295539, '图片上传', 'ImgUpload', 'MENU', 6, '/demo/upload', NULL, 'i-fe:image', '/src/views/demo/upload/index.vue', '', 1, NULL, NULL, 1, 1, 2);
INSERT INTO `t_menu_permission` VALUES (8, 1700295539, 1700295539, '个人资料', 'UserProfile', 'MENU', NULL, '/profile', NULL, 'i-fe:user', '/src/views/profile/index.vue', NULL, NULL, NULL, NULL, 0, 1, 99);
INSERT INTO `t_menu_permission` VALUES (9, 1700295539, 1700295539, '基础功能', 'Base', 'MENU', NULL, '', NULL, 'i-fe:grid', NULL, '', NULL, NULL, NULL, 1, 1, 0);
INSERT INTO `t_menu_permission` VALUES (10, 1700295539, 1700295539, '基础组件', 'BaseComponents', 'MENU', 9, '/base/components', NULL, 'i-me:awesome', '/src/views/base/index.vue', NULL, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO `t_menu_permission` VALUES (11, 1700295539, 1700295539, 'Unocss', 'Unocss', 'MENU', 9, '/base/unocss', NULL, 'i-me:awesome', '/src/views/base/unocss.vue', NULL, NULL, NULL, NULL, 1, 1, 2);
INSERT INTO `t_menu_permission` VALUES (12, 1700295539, 1700295539, 'keep_alive', 'keep_alive', 'MENU', 9, '/base/keep-alive', NULL, 'i-me:awesome', '/src/views/base/keep-alive.vue', NULL, 1, NULL, NULL, 1, 1, 3);
INSERT INTO `t_menu_permission` VALUES (13, 1700295539, 1700295539, '创建新用户', 'AddUser', 'BUTTON', 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO `t_menu_permission` VALUES (14, 1700295539, 1700295539, '图标 Icon', 'Icon', 'MENU', 9, '/base/icon', NULL, 'i-fe:feather', '/src/views/base/unocss-icon.vue', '', NULL, NULL, NULL, 1, 1, 0);
INSERT INTO `t_menu_permission` VALUES (15, 1700295539, 1700295539, 'MeModal', 'TestModal', 'MENU', 9, '/testModal', NULL, 'i-me:dialog', '/src/views/base/test-modal.vue', NULL, NULL, NULL, NULL, 1, 1, 5);

-- ----------------------------
-- Table structure for profile
-- ----------------------------
DROP TABLE IF EXISTS `t_profile`;
CREATE TABLE IF NOT EXISTS `t_profile`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `create_time` bigint DEFAULT 0,
    `update_time` bigint DEFAULT 0,
    `gender` int(11) NULL DEFAULT NULL,
    `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80',
    `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `user_id` bigint NOT NULL,
    `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uidx_profile_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of profile
-- ----------------------------
INSERT INTO `t_profile` VALUES (1, 1700295539, 1700295539, NULL, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', NULL, NULL, 1, 'Admin');

-- ----------------------------
-- Table structure for role_menus
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu_permission`;
CREATE TABLE IF NOT EXISTS `t_role_menu_permission`  (
    `role_id` int(11) NOT NULL,
    `permission_id` int(11) NOT NULL,
    PRIMARY KEY (`role_id`, `permission_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menus
-- ----------------------------
INSERT INTO `t_role_menu_permission` VALUES (2, 1);
INSERT INTO `t_role_menu_permission` VALUES (2, 2);
INSERT INTO `t_role_menu_permission` VALUES (2, 3);
INSERT INTO `t_role_menu_permission` VALUES (2, 4);
INSERT INTO `t_role_menu_permission` VALUES (2, 5);
INSERT INTO `t_role_menu_permission` VALUES (2, 9);
INSERT INTO `t_role_menu_permission` VALUES (2, 10);
INSERT INTO `t_role_menu_permission` VALUES (2, 11);
INSERT INTO `t_role_menu_permission` VALUES (2, 12);
INSERT INTO `t_role_menu_permission` VALUES (2, 14);
INSERT INTO `t_role_menu_permission` VALUES (2, 15);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE IF NOT EXISTS `t_user_role`  (
    `user_id` bigint NOT NULL,
    `role_id` int(11) NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    INDEX `idx_t_user_role_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1);
INSERT INTO `t_user_role` VALUES (1, 2);