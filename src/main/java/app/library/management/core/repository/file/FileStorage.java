package app.library.management.core.repository.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileStorage {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public FileStorage() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    /**
     * 파일 열기
     *
     * @return
     */
    public File openFile(String filePath) {
        File file = new File(filePath);
        return file;
    }

    /**
     * 파일 읽기
     *
     * @param file
     * @return
     */
    public List<BookVO> readFile(File file) {
        List<BookVO> list = new ArrayList<>();
        try {
            list = objectMapper.readValue(file, new TypeReference<List<BookVO>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 파일에 저장
     *
     * @param file
     * @param bookVO
     */
    public void saveFile(File file, BookVO bookVO) {
        try {
            List<BookVO> list = objectMapper.readValue(file, new TypeReference<List<BookVO>>(){});
            list.add(bookVO);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 파일에서 삭제
     *
     * @param file
     * @param bookVO
     */
    public void deleteFile(File file, BookVO bookVO) {
        try {
            List<BookVO> list = objectMapper.readValue(file, new TypeReference<List<BookVO>>(){});
            list.remove(bookVO);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 파일에 갱신
     *
     * @param file
     * @param bookVO
     */
    public void updateFile(File file, BookVO bookVO) {
        try {
            List<BookVO> list = objectMapper.readValue(file, new TypeReference<List<BookVO>>(){});
            Optional<BookVO> optional = list.stream().filter(b -> b.getId() == bookVO.getId())
                    .findAny();
            if (optional.isPresent()) {
                BookVO vo = optional.get();
                vo.setStatus(bookVO.getStatus());
                vo.setLastModifiedTime(bookVO.getLastModifiedTime());
            }
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
