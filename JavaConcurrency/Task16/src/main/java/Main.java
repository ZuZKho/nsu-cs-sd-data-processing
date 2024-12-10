import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscribers;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Can't find command line argument");
            System.exit(1);
        }

        String urlString = args[0];
        try {
            fetchUrlAsync(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fetchUrlAsync(String urlString) throws Exception {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<Void>> future = client
                    .sendAsync(request, responseInfo -> BodySubscribers.fromLineSubscriber(new ScreenRenderSubscriber()));

            future.join();
        }

    }


    private static class ScreenRenderSubscriber implements Flow.Subscriber<String> {
        private Flow.Subscription subscription;
        private final int screenSize = 25;
        private int linesLeft = screenSize;
        private final Scanner scanner = new Scanner(System.in);

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(linesLeft);
        }

        @Override
        public void onNext(String item) {
            linesLeft--;
            System.out.println(item);

            if (linesLeft == 0) {
                System.out.print("Press enter to scroll down...");
                scanner.nextLine();

                linesLeft = screenSize;
                subscription.request(screenSize);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onComplete() {
        }
    }
}
