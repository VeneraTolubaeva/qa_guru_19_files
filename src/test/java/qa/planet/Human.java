package qa.planet;

//{
//        "name": "Venera",
//        "age": 35,
//        "isClever": true,
//        "hobbies": ["Sport", "Reading"],
//        "passport": {
//        "number": 4040282828,
//        "issueDate": "12 Dec 2020"
//        }
//        }

import java.util.List;

public class Human {
    public String name;
    public Integer age;
    public Boolean isClever;
    public List<String> hobbies;
    public Passport passport;

    public static class Passport{
        public Long number;
        public String issueDate;
    }
}
