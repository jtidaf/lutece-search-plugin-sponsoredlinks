--
-- Dumping data for table core_admin_right
--
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url) VALUES 
('SPONSOREDLINKS_MANAGEMENT','sponsoredlinks.adminFeature.sponsoredlinks_management.name',2,'jsp/admin/plugins/sponsoredlinks/ManageSponsoredLinks.jsp','sponsoredlinks.adminFeature.sponsoredlinks_management.description',0,'sponsoredlinks','APPLICATIONS','images/admin/skin/plugins/sponsoredlinks/sposnoredlinks.png','jsp/admin/documentation/AdminDocumentation.jsp?doc=admin-sponsoredlinks');

--
-- Dumping data for table core_user_right
--
INSERT INTO core_user_right (id_right,id_user) VALUES ('SPONSOREDLINKS_MANAGEMENT',1);
INSERT INTO core_user_right (id_right,id_user) VALUES ('SPONSOREDLINKS_MANAGEMENT',2);

--
-- Dumping data for table core_admin_role
--
INSERT INTO core_admin_role (role_key,role_description) VALUES ('sponsoredlinks_manager','Gestion des liens valorisés');
INSERT INTO core_admin_role (role_key,role_description) VALUES ('sponsoredlinks_manager_admin','Paramétrage des blocs de liens');

--
-- Dumping data for table core_admin_role_resource
--
INSERT INTO core_admin_role_resource (rbac_id,role_key,resource_type,resource_id,permission) VALUES 
 (1100,'sponsoredlinks_manager_admin','SPONSOREDLINKS_TEMPLATE_TYPE','*','*');
INSERT INTO core_admin_role_resource (rbac_id,role_key,resource_type,resource_id,permission) VALUES 
 (1101,'sponsoredlinks_manager','SPONSOREDLINKS_SET_TYPE','*','*');
INSERT INTO core_admin_role_resource (rbac_id,role_key,resource_type,resource_id,permission) VALUES 
 (1102,'sponsoredlinks_manager','SPONSOREDLINKS_GROUP_TYPE','*','*');

--
-- Dumping data for table core_user_role
--
INSERT INTO core_user_role (role_key,id_user) VALUES ('sponsoredlinks_manager_admin',1);
INSERT INTO core_user_role (role_key,id_user) VALUES ('sponsoredlinks_manager',1);
INSERT INTO core_user_role (role_key,id_user) VALUES ('sponsoredlinks_manager',2);