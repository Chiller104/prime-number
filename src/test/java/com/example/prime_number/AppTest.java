package com.example.prime_number;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.apache.poi.ss.usermodel.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class AppTest {


    @Test
    public void testPrimeNumbers() {
        assertTrue(App.isPrime(1234187));
        assertTrue(App.isPrime(2147483647));
        assertTrue(App.isPrime(13));
        assertTrue(App.isPrime(29));
    }

    @Test
    public void testNonPrimeNumbers() {
        assertFalse(App.isPrime(1));
        assertFalse(App.isPrime(4));
        assertFalse(App.isPrime(10));
        assertFalse(App.isPrime(100));
        
        assertFalse(App.isPrime(0)); 
        assertFalse(App.isPrime(-5));
    }
    
    @Test
    public void testLargeNumber() {
        assertTrue(App.isPrime(9999999967L));
        assertFalse(App.isPrime(9999999966L));
    }

 
	@Test
	public void testValidExcelFile() throws Exception {

	    Workbook workbook = mock(Workbook.class);
	    Sheet sheet = mock(Sheet.class);
	    Row row1 = mock(Row.class);
	    Row row2 = mock(Row.class);
	    Row row3 = mock(Row.class);
	    Cell cell1 = mock(Cell.class);
	    Cell cell2 = mock(Cell.class);
	    Cell cell3 = mock(Cell.class);
	    
	    when(workbook.getSheetAt(0)).thenReturn(sheet);
	    when(sheet.iterator()).thenReturn(Arrays.asList(row1, row2, row3).iterator());

	    when(row1.getCell(1)).thenReturn(cell1); 
	    when(row2.getCell(1)).thenReturn(cell2); 
	    when(row3.getCell(1)).thenReturn(cell3); 
	    when(cell1.getCellType()).thenReturn(CellType.NUMERIC);
	    when(cell1.getNumericCellValue()).thenReturn(11.0);

	    when(cell2.getCellType()).thenReturn(CellType.NUMERIC);
	    when(cell2.getNumericCellValue()).thenReturn(4.0);
	    
	    when(cell3.getCellType()).thenReturn(CellType.NUMERIC);
	    when(cell3.getNumericCellValue()).thenReturn(3.14);

	    App.readExcelFile(workbook);

	    verify(cell1, times(2)).getNumericCellValue(); 
	    verify(cell2, times(2)).getNumericCellValue(); 
	    verify(cell3, times(1)).getNumericCellValue(); 
	}
	
	@Test
    public void testEmptyCellsAndInvalidData() throws Exception {
    	
        Cell mockCell = Mockito.mock(Cell.class);
        Mockito.when(mockCell.getCellType()).thenReturn(CellType.STRING);
        Mockito.when(mockCell.getStringCellValue()).thenThrow(new IllegalStateException("Invalid data"));

        Row mockRow = Mockito.mock(Row.class);
        Mockito.when(mockRow.getCell(1)).thenReturn(mockCell);

        Sheet mockSheet = Mockito.mock(Sheet.class);
        Iterator<Row> rowIterator = Collections.singletonList(mockRow).iterator();
        Mockito.when(mockSheet.iterator()).thenReturn(rowIterator);

        Workbook mockWorkbook = Mockito.mock(Workbook.class);
        Mockito.when(mockWorkbook.getSheetAt(0)).thenReturn(mockSheet);

        App.readExcelFile(mockWorkbook);

        Mockito.verify(mockCell).getStringCellValue();
    }
	
	
	@Test
	public void testEmptyWorkbook() throws Exception {
	    Workbook mockWorkbook = mock(Workbook.class);
	    Sheet mockSheet = mock(Sheet.class);
	    
	    when(mockWorkbook.getSheetAt(0)).thenReturn(mockSheet);
	    when(mockSheet.iterator()).thenReturn(Collections.emptyIterator());

	    App.readExcelFile(mockWorkbook);
	    
	    verify(mockSheet, times(1)).iterator();
	}

}