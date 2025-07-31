import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class BookScraper {
    public static void main(String[] args) throws IOException {
        String url = "http://books.toscrape.com/";
        Document doc = Jsoup.connect(url).get();
        Elements books = doc.select("article.product_pod");

        FileWriter csvWriter = new FileWriter("books.csv");
        csvWriter.append("Title,Price,Rating\n");

        for (Element book : books) {
            String title = book.select("h3 a").attr("title").replace(",", " ");
            String price = book.select(".price_color").text().replace("£", "");
            String ratingClass = book.select(".star-rating").attr("class");
            String rating = ratingClass.replace("star-rating", "").trim();

            csvWriter.append(title).append(',')
                     .append(price).append(',')
                     .append(rating).append('\n');
        }

        csvWriter.flush();
        csvWriter.close();

        System.out.println("Scraping completed. Data saved to books.csv");
    }
}
