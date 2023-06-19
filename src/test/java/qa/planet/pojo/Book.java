package qa.planet.pojo;

import java.util.List;

public class Book {
        private String author;
        private String title;
        private String genre;
        private List<String> mainCharacters;
        private List<Edition> editions;

        public String getAuthor() {
            return author;
        }

        public String getTitle() {
            return title;
        }

        public String getGenre() {
            return genre;
        }

        public List<String> getMainCharacters() {
            return mainCharacters;
        }

        public List<Edition> getEditions() {
            return editions;
        }

        public static class Edition {
            private String dateOfWriting;
            private Integer publishedYear;
            private String publisher;

            public String getDateOfWriting() {
                return dateOfWriting;
            }

            public Integer getPublishedYear() {
                return publishedYear;
            }

            public String getPublisher() {
                return publisher;
            }

        }
}
