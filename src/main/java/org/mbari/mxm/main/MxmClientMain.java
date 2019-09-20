package org.mbari.mxm.main;

import org.mbari.mxm.client.MxmClient;
import org.mbari.mxm.client.model.Executor;

import java.util.List;
import java.util.Optional;

/**
 * A simple main program demonstrating some of the operations
 * against a MXM service.
 */
public class MxmClientMain {
  public static void main(String[] args) {
    String endpoint = "http://tsauv.shore.mbari.org/mxm-graphql";
    MxmClient mxm = new MxmClient(endpoint);
    
    try {
      List<Executor> list = mxm.getAllExecutors();
      show("all executors response", list);

      list.forEach(e -> {
        Optional<Executor> x = mxm.getExecutor(e.executorId);
        show("particular executor executorId='" + e.executorId + "'",
            x.isPresent() ? x.get() : "not found"
        );
      });
    }
    catch (Exception ex) {
      System.err.printf("%s: %s%n", ex.getClass().getName(), ex.getMessage());
    }
  }
  
  private static void show(String title, Object o) {
    String s = "\n    " + o.toString().replaceAll("\n", "\n    ");
    System.out.println("\n==== " + title + " ====" + s);
  }
}
