package wang;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class TestFreemarker {

	public static void main(String[] args) throws IOException, TemplateException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
		cfg.setDirectoryForTemplateLoading(new File("../gzjy/src/test/java/wang"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		Template template = cfg.getTemplate("test.ftl");
		Map<String, String> data=new HashMap<String, String>();
		data.put("number", "081714318");
		data.put("name", "汪祥波");
		data.put("birthday", "1989年8月15日");
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("E://result.doc")),"utf-8"));
        template.process(data, out);
        out.flush();
	}
}
