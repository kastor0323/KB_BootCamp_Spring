package org.scoula.service;

import org.scoula.domain.AttachmentVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    // application.properties의 upload.path 값을 주입받습니다.
    @Value("${upload.path}")
    private String uploadPath;

    private Path rootPath;

    @PostConstruct
    public void init() throws IOException {

        // 문자열 경로를 Path 객체로 변환합니다.
        rootPath = Paths.get(uploadPath)
                .toAbsolutePath()
                .normalize();

        // 업로드 폴더가 없으면 자동으로 생성합니다.
        Files.createDirectories(rootPath);
    }

    public AttachmentVO store(
            Long bno,
            MultipartFile file)
            throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "업로드할 파일이 없습니다."
            );
        }

        // 브라우저에서 전달된 원래 파일명을 가져옵니다.
        String originalName =
                file.getOriginalFilename();

        if (originalName == null ||
                originalName.isBlank()) {

            throw new IllegalArgumentException(
                    "파일 이름을 확인할 수 없습니다."
            );
        }

        // 일부 브라우저가 전체 경로를 전달할 수 있으므로
        // 순수 파일명만 추출합니다.
        originalName = Paths
                .get(originalName)
                .getFileName()
                .toString();

        // 확장자를 추출합니다.
        String extension =
                getExtension(originalName);

        // 서버에 저장할 UUID 파일명을 만듭니다.
        //Universally Unique Identifier
    /*
   originalName 강아지.jpg
   extension.jpg
   UUID.randomUUID()
   83d522f-779e-4a1c-b6e8-0ed44739e669
   storedName
   f83d522f-779e-4a1c-b6e8-0ed44739e669.jpg
    */
        String storedName =
                UUID.randomUUID() + extension;

        Path targetPath =
                rootPath.resolve(storedName)
                        .normalize();

        // 업로드 루트 폴더 밖으로 벗어나는 경로를 차단합니다.
        if (!targetPath.startsWith(rootPath)) {
            throw new SecurityException(
                    "올바르지 않은 파일 경로입니다."
            );
        }

        // 실제 파일을 저장합니다.
        file.transferTo(targetPath.toFile());

        String contentType =
                file.getContentType();

        boolean imageFile =
                contentType != null &&
                        contentType.startsWith("image/");

        // DB에 저장할 파일 정보를 반환합니다.
        return AttachmentVO.builder()
                .bno(bno)
                .originalName(originalName)
                .storedName(storedName)
                .contentType(contentType)
                .fileSize(file.getSize())
                .imageFile(imageFile)
                .build();
    }

    public Path load(
            String storedName) {

        Path filePath =
                rootPath.resolve(storedName)
                        .normalize();

        if (!filePath.startsWith(rootPath)) {
            throw new SecurityException(
                    "올바르지 않은 파일 경로입니다."
            );
        }

        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException(
                    "저장된 파일을 찾을 수 없습니다."
            );
        }

        return filePath;
    }

    public void delete(
            String storedName)
            throws IOException {

        Path filePath =
                rootPath.resolve(storedName)
                        .normalize();

        if (!filePath.startsWith(rootPath)) {
            throw new SecurityException(
                    "올바르지 않은 파일 경로입니다."
            );
        }

        // 파일이 있으면 삭제하고 없어도 예외를 발생시키지 않습니다.
        Files.deleteIfExists(filePath);
    }

    private String getExtension(
            String fileName) {

        int index =
                fileName.lastIndexOf(".");

        if (index == -1) {
            return "";
        }

        return fileName.substring(index);
    }

}