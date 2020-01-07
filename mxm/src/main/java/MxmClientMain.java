import org.mbari.mxm.client.MxmClient;
import org.mbari.mxm.client.model;
import org.mbari.mxm.client.model.Executor;

import java.util.List;
import java.util.Optional;

/**
 * A simple main program demonstrating some of the operations against
 * an MXM service.
 *
 * Assuming your CLASSPATH includes `mxm-java-client-x.y.z.jar` and
 * the httpclient and gson libraries,
 * you can run it on the command line like this:
 *
 * <code>
 *   java MxmClientMain http://tsauv.shore.mbari.org/mxm-graphql
 * </code>
 */
public class MxmClientMain {
  public static void main(String[] args) {
    String endpoint = args.length == 1 ? args[0] : "http://tsauv.shore.mbari.org/mxm-graphql";
  
    System.out.println("mxm-client library version: " + MxmClient.getVersion());
    
    MxmClient mxm = new MxmClient(endpoint);
    
    // general info about executors:
    List<Executor> executors = mxm.getExecutors();
    show("all executors response", executors);

    executors.forEach(e -> {
      // more detailed info about each executor:
      Optional<Executor> ox = mxm.getExecutor(e.executorId);
      if (ox.isPresent()) {
        Executor x = ox.get();
        show("EXECUTOR executorId='" + e.executorId + "'", x);
        
        x.missionTemplates.forEach( mtBasic -> {
          // more detailed info about each mission template:
          Optional<model.MissionTemplate> omt = mxm.getExecutorMissionTemplate(x.executorId, mtBasic.missionTplId);
          if (omt.isPresent()) {
            model.MissionTemplate mt = omt.get();
            show("MISSION TEMPLATE missionTplId='" + mtBasic.missionTplId + "'", mt);
          }
          else {
            show("MISSION TEMPLATE missionTplId='" + mtBasic.missionTplId + "'", "not found");
          }

          // general info about missions from this executor:
          List<model.Mission> missions = mxm.getExecutorMissions(x.executorId, mtBasic.missionTplId);
          show("missions for missionTplId='" + mtBasic.missionTplId + "'", missions);

          missions.forEach( mBasic -> {
            // more detailed info about each mission:
            Optional<model.Mission> om = mxm.getMission(x.executorId, mtBasic.missionTplId, mBasic.missionId);
            String label = String.format("MISSION missionTplId='%s' missionId='%s'", mtBasic.missionTplId, mBasic.missionId);
            if (om.isPresent()) {
              model.Mission m = om.get();
              show(label, m);
            }
            else {
              show(label, "not found");
            }
          });
        });
      }
      else {
        show("executor executorId='" + e.executorId + "'","not found");
      }
    });
  }
  
  private static void show(String title, Object o) {
    String s = "\n    " + o.toString().replaceAll("\n", "\n    ");
    System.out.println("\n==== " + title + " ====" + s);
  }
}
