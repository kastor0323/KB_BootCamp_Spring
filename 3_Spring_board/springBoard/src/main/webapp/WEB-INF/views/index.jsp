<%@ page
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<!DOCTYPE html> <html lang="ko"> <head> <meta charset="UTF-8">
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1">

    <title>Spring Board</title>

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

    <link
            href="${pageContext.request.contextPath}/resources/css/board.css"
            rel="stylesheet">
</head> <body> <nav class="navbar navbar-expand-lg bg-white shadow-sm">
    <div class="container">

        <a
                class="navbar-brand text-primary"
                href="${pageContext.request.contextPath}/">

            Spring Board

        </a>

        <div class="navbar-nav ms-auto">

            <a
                    class="nav-link"
                    href="${pageContext.request.contextPath}/board/data">

                자료실

            </a>

            <a
                    class="nav-link"
                    href="${pageContext.request.contextPath}/board/gallery">

                갤러리

            </a>

        </div>

    </div>
</nav> <main class="container py-5">
    <section class="page-header text-center mb-5">

        <h1 class="display-5 fw-bold">
            Spring 자료실·갤러리 게시판
        </h1>

        <p class="lead text-secondary mt-3 mb-0">
            파일 업로드와 다운로드를 지원하는
            Spring Legacy 게시판입니다.
        </p>

    </section>

    <div class="row g-4">

        <div class="col-md-6">

            <div class="card content-card h-100">

                <div class="card-body p-5">

                <span class="badge text-bg-primary mb-3">
                    DATA
                </span>

                    <h2 class="h3 fw-bold">
                        자료실
                    </h2>

                    <p class="text-secondary mt-3">
                        수업 자료, 문서, 압축 파일 등
                        다양한 파일을 등록하고 내려받을 수 있습니다.
                    </p>

                    <a
                            href="${pageContext.request.contextPath}/board/data"
                            class="btn btn-primary mt-3">

                        자료실로 이동

                    </a>

                </div>

            </div>

        </div>

        <div class="col-md-6">

            <div class="card content-card h-100">

                <div class="card-body p-5">

                <span class="badge text-bg-success mb-3">
                    GALLERY
                </span>

                    <h2 class="h3 fw-bold">
                        갤러리
                    </h2>

                    <p class="text-secondary mt-3">
                        이미지를 업로드하고 카드 형태로
                        미리 볼 수 있는 갤러리입니다.
                    </p>

                    <a
                            href="${pageContext.request.contextPath}/board/gallery"
                            class="btn btn-success mt-3">

                        갤러리로 이동

                    </a>

                </div>

            </div>

        </div>

    </div>
</main> </body> </html>