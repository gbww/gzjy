package wang;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Test {
    public static void main(String args[]){
        String inFilePath = "D://template.xls";
        String outFilePath = "D://result.pdf";
        xlsToPdf(inFilePath, outFilePath);
    }

    public static String xlsToPdf(String inFilePath,String outFilePath){
        ComThread.InitSTA(true);
        ActiveXComponent ax=new ActiveXComponent("Excel.Application");
        try{
            ax.setProperty("Visible", new Variant(false));
            ax.setProperty("AutomationSecurity", new Variant(3)); //禁用宏
            Dispatch excels=ax.getProperty("Workbooks").toDispatch();

            Dispatch excel=Dispatch.invoke(excels,"Open",Dispatch.Method,new Object[]{
                            inFilePath,
                            new Variant(false),
                            new Variant(false)
                    },
                    new int[9]).toDispatch();
            //转换格式
            Dispatch.invoke(excel,"ExportAsFixedFormat", Dispatch.Method,new Object[]{
                    new Variant(0), //PDF格式=0
                    outFilePath,
                    new Variant(0)  //0=标准 (生成的PDF图片不会变模糊) 1=最小文件 (生成的PDF图片糊的一塌糊涂)
            },new int[1]);
            Dispatch.call(excel, "Close",new Variant(false));

            if(ax!=null){
                ax.invoke("Quit",new Variant[]{});
                ax=null;
            }
            ComThread.Release();
            return "";
        }catch(Exception es){
            return es.toString();
        }
    }
}
