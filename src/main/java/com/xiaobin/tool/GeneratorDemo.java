package com.xiaobin.tool;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.generator.*;
import com.jfinal.plugin.c3p0.C3p0Plugin;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * GeneratorDemo
 */
public class GeneratorDemo {

	public static DataSource getDataSource() {
		Prop p = PropKit.use("c3p0.properties");
		C3p0Plugin c3p0Plugin = new C3p0Plugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
		c3p0Plugin.setDriverClass(p.get("driverClass"));
		c3p0Plugin.start();
		return c3p0Plugin.getDataSource();
	}
	
	public static void main(String[] args) {
		// base model 所使用的包名
		String baseModelPackageName = "com.xiaobin.model.base";
		// base model 文件保存路径
		String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/xiaobin/model/base";
		
		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.xiaobin.model.model";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/xiaobin/model/model";

		// 创建生成器
		Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		// 设置数据库方言
		gernerator.setDialect(new OracleDialect());
		// 添加不需要生成的表名
//		gernerator.addExcludedTable("table_column_comments");
		MyBuilder builder = new MyBuilder(getDataSource());
		builder.setDialect(new OracleDialect());
		builder.setTypeMapping(new TypeMapping(){

			@SuppressWarnings("serial")
			private Map<String, String> map = new HashMap<String, String>() {{
				// java.util.Data can not be returned
				// java.sql.Date, java.sql.Time, java.sql.Timestamp all extends java.util.Data so getDate can return the three types data
				// put("java.util.Date", "java.util.Date");

				// date, year
				put("java.sql.Date", "java.util.Date");

				// time
				put("java.sql.Time", "java.util.Date");

				// timestamp, datetime
				put("java.sql.Timestamp", "java.util.Date");

				// binary, varbinary, tinyblob, blob, mediumblob, longblob
				// qjd project: print_info.content varbinary(61800);
				put("[B", "byte[]");

				// ---------

				// varchar, char, enum, set, text, tinytext, mediumtext, longtext
				put("java.lang.String", "java.lang.String");

				// int, integer, tinyint, smallint, mediumint
				put("java.lang.Integer", "java.lang.Integer");

				// bigint
				put("java.lang.Long", "java.lang.Long");

				// real, double
				put("java.lang.Double", "java.lang.Double");

				// float
				put("java.lang.Float", "java.lang.Float");

				// bit
				put("java.lang.Boolean", "java.lang.Boolean");

				// decimal, numeric
				put("java.math.BigDecimal", "java.math.BigDecimal");

				// unsigned bigint
				put("java.math.BigInteger", "java.math.BigInteger");

				put("oracle.sql.TIMESTAMP","java.sql.Timestamp");
			}};

			public String getType(String typeString) {
				return map.get(typeString);
			}
		});
		gernerator.setMetaBuilder(builder);
		// 设置是否在 Model 中生成 dao 对象
		gernerator.setGenerateDaoInModel(true);
		// 设置是否生成字典文件
		gernerator.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
		gernerator.setRemovedTableNamePrefixes("SOAP_");
		// 生成
		gernerator.generate();
	}

	static class MyBuilder extends MetaBuilder{

		public MyBuilder(DataSource dataSource) {
			super(dataSource);
		}

		@Override
		protected boolean isSkipTable(String tableName) {
			return super.isSkipTable(tableName) || !tableName.matches("^(?:(?i)soap)_.*$");
		}

		/**
		 * 重写使table_name满足java类名的驼峰命名规则
		 * @param tableName
		 * @return
		 */
		@Override
		protected String buildModelName(String tableName) {
			if(super.removedTableNamePrefixes != null) {
				String[] arr$ = super.removedTableNamePrefixes;
				int len$ = arr$.length;

				for(int i$ = 0; i$ < len$; ++i$) {
					String prefix = arr$[i$];
					if(tableName.startsWith(prefix)) {
						tableName = tableName.replaceFirst(prefix, "");
						break;
					}
				}
			}

			tableName = tableName.toLowerCase();

			return StrKit.firstCharToUpperCase(StrKit.toCamelCase(tableName));
		}

		@Override
		protected String buildAttrName(String colName) {
			return super.buildAttrName(colName);
		}

		@Override
		protected void buildColumnMetas(TableMeta tableMeta) throws SQLException {
			String sql = dialect.forTableBuilderDoBuild(tableMeta.name);
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			for (int i=1; i<=rsmd.getColumnCount(); i++) {
				ColumnMeta cm = new ColumnMeta();
				cm.name = rsmd.getColumnName(i).toLowerCase();

				String colClassName = rsmd.getColumnClassName(i);
				String typeStr = typeMapping.getType(colClassName);
				if (typeStr != null) {
					cm.javaType = typeStr;
				}
				else {
					int type = rsmd.getColumnType(i);
					if (type == Types.BINARY || type == Types.VARBINARY || type == Types.BLOB) {
						cm.javaType = "byte[]";
					}
					else if (type == Types.CLOB || type == Types.NCLOB) {
						cm.javaType = "java.lang.String";
					}
					else {
						cm.javaType = "java.lang.String";
					}
				}

				// 构造字段对应的属性名 attrName
				cm.attrName = buildAttrName(cm.name);

				tableMeta.columnMetas.add(cm);
			}

			rs.close();
			stm.close();
		}
	}
}




