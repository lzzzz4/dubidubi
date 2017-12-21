package cn.dubidubi.dao.generator;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Start {
	public void generator() throws Exception {

		List<String> warnings = new ArrayList<>();
		boolean overwrite = true;
		// 指定 逆向工程配置文件
		// File configFile = new File("generatorConfig.xml");
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(inputStream);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);

	}

	/**
	 * 在db.properties中修改了数据库连接,在逆向工程中的配置文档也需要修改
	 */
	public static void main(String[] args) throws Exception {
		try {
			Start generatorSqlmap = new Start();
			generatorSqlmap.generator();
			System.out.println("hello");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
