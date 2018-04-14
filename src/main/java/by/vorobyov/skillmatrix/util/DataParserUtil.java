package by.vorobyov.skillmatrix.util;

import by.vorobyov.skillmatrix.domain.Node;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


@Component
public class DataParserUtil {

    private IndexProvider indexProvider;
    private int rowNumGlobal;
    @Autowired
    public DataParserUtil(IndexProvider indexProvider) {
        this.indexProvider = indexProvider;
    }

    public Node parseXLS(List<Node> list, File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = new HSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Node node = null;
            if (sheet.getRow(0) != null && sheet.getRow(0).getCell(0) != null) {
                node = parse(list, sheet, 0, 0);
            }
            return node;
            //todo pause point
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Node parse(List<Node> list, Sheet sheet, int rowNum, int colNum) {
        boolean stopFlag = true;
        Node node = new Node(indexProvider.getLastIndex(), sheet.getRow(rowNum).getCell(colNum).getStringCellValue());
        list.add(node);
        int cRowNum = rowNum + 1;
        int cCellNum = colNum + 1;

        Row row = sheet.getRow(cRowNum);
        while (stopFlag) {
            if(row == null){
                stopFlag = false;
                rowNumGlobal = cRowNum-1;
            }else {
                Cell cell = row.getCell(cCellNum);
                if (cell == null || cell.getStringCellValue().trim().isEmpty()) {
                    stopFlag = false;
                    rowNumGlobal = cRowNum - 1;
                } else {
                    node.getSubs().add(parse(list, sheet, cRowNum, cCellNum));
                    cRowNum = rowNumGlobal;
                    row = sheet.getRow(++cRowNum);
                }
            }
        }

        return node;
    }

}
