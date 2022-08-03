import org.mbari.mxm.client.MxmClient;
import org.mbari.mxm.client.model.*;

import java.util.List;
import java.util.Optional;

/**
 * A simple main program demonstrating some operations against
 * an MXM service.
 * <p>
 * Assuming your CLASSPATH includes `mxm-java-client-x.y.z.jar` and
 * the httpclient and gson libraries,
 * you can run it on the command line like this:
 * <p>
 * <code>
 *   java MxmClientMain http://mxm.shore.mbari.org/mxm-graphql
 * </code>
 */
public class MxmClientMain {
  public static void main(String[] args) {
    String endpoint = args.length == 1 ? args[0] : "http://mxm.shore.mbari.org/mxm-graphql";

    System.out.println("mxm-client library version: " + MxmClient.getVersion());

    MxmClient mxm = new MxmClient(endpoint);

    // general info about providers:
    List<Provider> providers = mxm.getProviders();
    show("all providers response", providers);

    providers.forEach(e -> {
      // more detailed info about each provider:
      Optional<Provider> ox = mxm.getProvider(e.providerId);
      if (ox.isPresent()) {
        Provider x = ox.get();
        show("PROVIDER providerId='" + e.providerId + "'", x);

        x.missionTemplates.forEach( mtBasic -> {
          // more detailed info about each mission template:
          Optional<MissionTemplate> omt = mxm.getProviderMissionTemplate(x.providerId, mtBasic.missionTplId);
          if (omt.isPresent()) {
            MissionTemplate mt = omt.get();
            show("MISSION TEMPLATE missionTplId='" + mtBasic.missionTplId + "'", mt);
          }
          else {
            show("MISSION TEMPLATE missionTplId='" + mtBasic.missionTplId + "'", "not found");
          }

          // general info about missions from this provider:
          List<Mission> missions = mxm.getProviderMissions(x.providerId, mtBasic.missionTplId);
          show("missions for missionTplId='" + mtBasic.missionTplId + "'", missions);

          missions.forEach( mBasic -> {
            // more detailed info about each mission:
            Optional<Mission> om = mxm.getMission(x.providerId, mtBasic.missionTplId, mBasic.missionId);
            String label = String.format("MISSION missionTplId='%s' missionId='%s'", mtBasic.missionTplId, mBasic.missionId);
            if (om.isPresent()) {
              Mission m = om.get();
              show(label, m);
            }
            else {
              show(label, "not found");
            }
          });
        });
      }
      else {
        show("provider providerId='" + e.providerId + "'","not found");
      }
    });
  }

  private static void show(String title, Object o) {
    String s = "\n    " + o.toString().replaceAll("\n", "\n    ");
    System.out.println("\n==== " + title + " ====" + s);
  }
}
