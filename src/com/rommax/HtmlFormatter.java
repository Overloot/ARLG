package com.rommax;

/**
 * Created by Maxim on 13.11.2015.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author Урванов Ф. В.
 * Класс, преобразующий лог в формат HTML.
 *
 */
public class HtmlFormatter extends Formatter {

    public HtmlFormatter()
    {

    }

    /**
     * Возвращаем заголовочную часть HTML файла.
     */
    @Override
    public String getHead(Handler h)
    {
        /**
         * Записываем заголовок HTML файла, мета информацию и начало таблицы.
         */
        return "<html><head><title>AppLog</title>" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">" +
                "</head><body>" +
                "<table border=1>" +
                "<tr bgcolor=CYAN><td>date</td><td>level</td><td>class</td><td>method</td>" +
                "<td>message</td><td>thrown message</td><td>stacktrace</td></tr>";
    }


    /**
     * Возвращаем хвост (конец) HTML файла.
     */
    @Override
    public String getTail(Handler h)
    {
        /**
         * Записываем окончание таблицы и конец HTML файла.
         */
        return "</table></body></html>";
    }

    /**
     * Форматируем одно сообщение в строку таблицы.
     */
    @Override
    public String format(LogRecord record)
    {
        StringBuilder result=new StringBuilder();
        Date d = new Date();
        Level level = record.getLevel();

        /**
         * Ошибки будут выделены красным цветом,
         * предупреждения - серым,
         * информационные сообщения - белым.
         */
        if (level==Level.SEVERE)
        {
            result.append("<tr bgColor=Tomato><td>");
        }
        else if (level==Level.WARNING)
        {
            result.append("<tr bgColor=GRAY><td>");
        }
        else
        {
            result.append("<tr bgColor=WHITE><td>");
        }

        result.append("\n");


        result.append(d);
        result.append("</td><td>");
        result.append(record.getLevel().toString());
        result.append("</td><td>");
        result.append(record.getSourceClassName());
        result.append("</td><td>");
        result.append(record.getSourceMethodName());
        result.append("</td><td>");
        result.append(record.getMessage());
        result.append("</td><td>");



        Throwable thrown = record.getThrown();



        if (thrown!=null)
        {
            // Если было передано исключение, то выводим полный
            // стек вызовов.
            result.append(record.getThrown().getMessage());
            result.append("</td><td>");

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            String stackTrace=sw.toString();

            result.append(stackTrace);
            result.append("</td>");
        }
        else
        {
            // Просто пустые ячейки.
            result.append("</td><td>null");
            result.append("</td>");
        }

        // Конец строки
        result.append("</tr>\n");
        return result.toString();
    }
}
