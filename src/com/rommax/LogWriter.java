package com.rommax;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by Maxim on 13.11.2015.
 */
public class LogWriter {

    public void toHTML(Class cl) {

        // Получаем экземпляр класса, который будем использовать для записи логов.
        // Передаваемый параметр LogApp - имя логера.
        // В документации сказано, что имя логгера должно совпадать с именем
        // класса или пакета ().
        Logger logger = Logger.getLogger(cl.getName());

        // Создаём handler, который будет записывать лог
        // в файл "LogApp". Символ "%t" указывает на то, что файл
        // будет располагаться в папке с системными временными файлами.
        try {
            // Оставляем предыдущий handler (будет создаваться файл "LogApp")
            FileHandler fh = new FileHandler("%tARLG.log");
            logger.addHandler(fh);

            // Добавляем ещё файл "LogApp.htm".
            HtmlFormatter htmlformatter = new HtmlFormatter();
            FileHandler htmlfile = new FileHandler("%tARLG_log.htm");
            // Устанавливаем html форматирование с помощью класса HtmlFormatter.
            htmlfile.setFormatter(htmlformatter);
            logger.addHandler(htmlfile);
        } catch (SecurityException e) {
            logger.log(Level.SEVERE, "Не удалось создать файл лога из-за политики безопасности.", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Не удалось создать файл лога из-за ошибки ввода-вывода.", e);
        }

        logger.log(Level.INFO, "Запись лога с уровнем INFO (информационная)");
        logger.log(Level.WARNING, "Запись лога с уровнем WARNING (Предупреждение)");
        logger.log(Level.SEVERE, "Запись лога с уровнем SEVERE (серъёзная ошибка)",
                new Exception("Проверочное сообщение об ошибке"));
    }

}
