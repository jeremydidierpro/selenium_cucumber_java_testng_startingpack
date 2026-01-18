package functions.excel;

import exceptions.NotFoundValueException;
import functions.miscellaneous.PathFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.IntStream;

public class ExcelManager {
    protected int columnIndex;
    protected int rowIndex;
    protected String pathToFile;
    protected FileInputStream fis;
    protected FileOutputStream fos;
    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;
    private final Logger logger = LogManager.getLogger(ExcelManager.class);


    /**
     * First constructor with only the relative path and the sheet's name of the excel file needed
     * @param relativePathToFile path from the root of the project
     * @param nameOfSheet sheet
     */
    public ExcelManager(String relativePathToFile, String nameOfSheet){
        this.pathToFile = PathFinder.getPath(relativePathToFile);
        try {
            this.fis = new FileInputStream(pathToFile);
            this.workbook = new XSSFWorkbook(this.fis);
        } catch (IOException e) {
            logger.error("Something went wrong while instancing ExcelManager class :: ", e);
            e.printStackTrace();
        }
        this.sheet = workbook.getSheet(nameOfSheet);
    }

    /**
     * Second constructor with the relative path, the sheet's name and the raw that will be used on the excel file
     * @param relativePathToFile path from the root of the project
     * @param nameOfSheet sheet
     * @param rowIndex row index
     */
    public ExcelManager(String relativePathToFile, String nameOfSheet, int rowIndex){
        this.pathToFile = PathFinder.getPath(relativePathToFile);
        try {
            this.fis = new FileInputStream(pathToFile);
            this.workbook = new XSSFWorkbook(this.fis);
        } catch (IOException e) {
            logger.error("Something went wrong while instancing ExcelManager class : ", e);
            e.printStackTrace();
        }
        this.sheet = workbook.getSheet(nameOfSheet);
        this.rowIndex = rowIndex;
    }

    /**
     * Third constructor with the relative path, the sheet's name and the raw that will be used on the excel file
     * @param relativePathToFile path from the root of the project
     * @param nameOfSheet sheet
     * @param rowName row name
     */
    public ExcelManager(String relativePathToFile, String nameOfSheet, String rowName){
        this.pathToFile = PathFinder.getPath(relativePathToFile);
        try {
            this.fis = new FileInputStream(pathToFile);
            this.workbook = new XSSFWorkbook(this.fis);
        } catch (IOException e) {
            logger.error("Something went wrong while instancing ExcelManager class : ", e);
            e.printStackTrace();
        }
        this.sheet = workbook.getSheet(nameOfSheet);
        setRow(rowName);
    }

    /**
     * First method to return the value of a excel file by giving the row index and the column index
     * Attention the index begin by 0.
     * @param rowIndex row index
     * @param columnIndex col index
     * @return the cell value
     */
    public String read(int rowIndex, int columnIndex){
        XSSFCell cellToRead = this.sheet.getRow(rowIndex).getCell(columnIndex);
        return getStringValue(cellToRead);
    }

    /**
     * Second method to return the value of a excel file. You just  need to specify the nome of the row.
     *
     * @return the cell value
     */
    public String read(String nameOfColumn){
        setColumn(nameOfColumn);
        XSSFCell cellToRead = this.sheet.getRow(rowIndex).getCell(columnIndex);
        return getStringValue(cellToRead);
    }

    /**
     * This methods returns a String whatever the value of the cell is.
     * @param cellToRead cell to read
     * @return the cell value
     */
    public String getStringValue(XSSFCell cellToRead){
        switch(cellToRead.getCellType()){
            case STRING:
                return cellToRead.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cellToRead.getBooleanCellValue());
            case NUMERIC:
                return String.valueOf(cellToRead.getNumericCellValue());
            default:
                return cellToRead.getRawValue();
        }
    }

    /**
     * First method to write in the excel. We need to provide the index of the row, the index of the column and the text to write
     * @param rowIndex row index
     * @param columnIndex col index
     * @param text value to write
     */
    public void write(int rowIndex, int columnIndex, String text){
        try {
            XSSFCell cellToUpdate = this.sheet.getRow(rowIndex).getCell(columnIndex);
            cellToUpdate.setCellValue(text);
            this.fos = new FileOutputStream(pathToFile);
            this.workbook.write(this.fos);
            this.fos.close();
        } catch (IOException e) {
            logger.error(() -> "Something went wrong while writing '"+text+"' into the Excel file :: ", e);
            e.printStackTrace();
        }
    }

    /**
     * Second method to write in the excel. We just need to provide the name of the column and the text to write
     * @param nameOfColumn name of col
     * @param text value to write
     */
    public void write(String nameOfColumn, String text){
        setColumn(nameOfColumn);
        try {
            XSSFCell cellToUpdate = this.sheet.getRow(this.rowIndex).getCell(this.columnIndex);
            cellToUpdate.setCellValue(text);
            this.fos = new FileOutputStream(pathToFile);
            this.workbook.write(this.fos);
            this.fos.close();
        } catch (IOException e) {
            logger.error(() -> "Something went wrong while writing '"+text+"' into the Excel file :: ", e);
            e.printStackTrace();
        }
    }

    /**
     * Uses the provided name of the column in order to find the index of the row and set it in the class variable to work on the same column all along
     * @param columnName the name of the col
     */
    public void setColumn(String columnName){
        IntStream.rangeClosed(0,getLastCol())
                .filter(col -> read(0, col).equals(columnName))
                .forEach(col -> this.columnIndex = col);
        if(this.columnIndex == -1){
            logger.error(() -> "The column "+columnName+ " hasn't been found");
            throw new NotFoundValueException("The column "+columnName+ " has not been found");
        }
    }

    /**
     * Uses the provided name of the row in order to find the index of the row and set it in the class variable to work on the same row all along
     * @param rowName name of the row
     */
    public void setRow(String rowName){
        this.rowIndex = -1;
        IntStream.rangeClosed(0,getLastRow())
                .filter(row -> read(row, 0).equals(rowName))
                .forEach(row -> this.rowIndex = row);
        if(this.rowIndex == -1){
            logger.error(() -> "The row "+rowName+ " hasn't been found");
            throw new NotFoundValueException("The row "+rowName+ " has not been found");
        }
    }

    /**
     * Returns the index of the last column used
     * @return index of the last col
     */
    public int getLastCol(){
        return this.sheet.getRow(0).getLastCellNum()-1;
    }

    /**
     * Returns the index of the last row used
     * @return index of the last raw
     */
    public int getLastRow(){
       return this.sheet.getLastRowNum();
      }

    /**
     * Resets the raw of the concerned process
     */
    public void resetRow(){
        IntStream.rangeClosed(1,getLastCol())
                .forEach(cellToReset -> write(this.rowIndex, cellToReset, ""));
    }


}
