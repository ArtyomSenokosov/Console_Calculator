package ru.mail.senokosov.artem;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import ru.mail.senokosov.artem.calculation.Calculator;
import ru.mail.senokosov.artem.calculation.CalculatorImpl;
import ru.mail.senokosov.artem.converter.ReversePolishNotationConverter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void main() throws IOException {

        FileInputStream file = new FileInputStream("src/test/TestData/DataTest.xlsx");
        XSSFWorkbook myExcelBook = new XSSFWorkbook(file);

        XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
        Iterator<Row> rows = myExcelSheet.rowIterator();

        while (rows.hasNext()) {
            XSSFRow row = (XSSFRow) rows.next();
            Iterator<Cell> cells = row.cellIterator();

            String term, resultException = null;
            double result = 0;

            XSSFCell cell = (XSSFCell) cells.next();
            term = cell.getStringCellValue();

            cell = (XSSFCell) cells.next();
            if (cell.getCellType() == CellType.NUMERIC) {
                result = cell.getNumericCellValue();
            } else {
                resultException = cell.getStringCellValue();
            }

            try {
                List<String> rpn = ReversePolishNotationConverter.sortingStation(term);
                Calculator calculator = new CalculatorImpl();
                assertEquals(result, calculator.calculate(rpn).pop().doubleValue(), 0.001);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                assertEquals(resultException, e.getMessage());
            }
        }
    }
}