package com.example.prime_number;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class App {
	
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {

    	setupLogger();
        
        if (args.length < 1) {
            logger.severe("FileName is missing");
            return;
        }

        String fileName = args[0];
        
        try (FileInputStream fis = new FileInputStream(new File(fileName))) {
            Workbook workbook = new XSSFWorkbook(fis);
            readExcelFile(workbook);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    public static void readExcelFile(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = row.getCell(1);

            if (cell != null) {
                double value = 0;

                try {
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            if (cell.getNumericCellValue() % 1 == 0) {
                                value = cell.getNumericCellValue();
                                if (value > 0 && isPrime((long) value)) {
                                    logger.info("Prime number is: " + (long) value);
                                }
                            }
                            break;
                        case STRING:
                            try {
                                value = Double.parseDouble(cell.getStringCellValue());
                                if (value > 0 && isPrime((long) value)) {
                                    logger.info("Prime number is: " + (long) value);
                                }
                            } catch (NumberFormatException e) {
                                break;
                            }
                            break;
                    }
                } catch (IllegalStateException e) {
                    logger.severe("Invalid data in cell: " + e.getMessage());
                    logger.severe("Invalid number format: " + e.getMessage());
                }
            }
        }
    }

    public static boolean isPrime(long l) {
        if (l <= 1) return false;
        if (l == 2) return true;
        if (l % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(l); i += 2) {
            if (l % i == 0) return false;
        }
        return true;
    }
    
    public static void setupLogger() {
    	
        logger.setLevel(Level.ALL);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        
        SimpleFormatter formatter = new SimpleFormatter() {
            private static final String format = "%1$tF %1$tT (%2$s) - %3$s | %4$s %5$s%n";
            
            @Override
            public synchronized String format(java.util.logging.LogRecord lr) {
                return String.format(format, 
                                     new java.util.Date(lr.getMillis()),        
                                     lr.getLevel().getLocalizedName(),           
                                     lr.getSourceMethodName(),                  
                                     lr.getMessage(),                            
                                     (lr.getThrown() != null ? " Exception: " + lr.getThrown() : ""));
            }
        };

        System.out.println("Timestamp          |Level|    Method           Message");
        ch.setFormatter(formatter);
        logger.addHandler(ch);
        
        Logger globalLogger = Logger.getLogger("");
        for (Handler handler : globalLogger.getHandlers()) {
            globalLogger.removeHandler(handler);
        }
    }
    
}


