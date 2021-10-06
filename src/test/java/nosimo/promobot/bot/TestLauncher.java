package nosimo.promobot.bot;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

import static java.lang.System.out;

public class TestLauncher {

    public static void main(String[] args) {
        var launcher = LauncherFactory.create();
//        launcher.registerLauncherDiscoveryListeners();

        var summaryGeneratingListener = new SummaryGeneratingListener();
//        launcher.registerTestExecutionListeners(summaryGeneratingListener);

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
//                .selectors(DiscoverySelectors.selectClass(ProcessingUserMessageTest.class));
                .selectors(DiscoverySelectors.selectPackage("nosimo.promobot.bot"))
                .build();
        launcher.execute(request, summaryGeneratingListener);

        try (var writer = new PrintWriter(out)) {
            summaryGeneratingListener.getSummary().printTo(writer);
        }

    }
}
