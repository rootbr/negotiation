package org.rootbr.negotiation;

import java.util.Collections;
import java.util.Random;
import org.camunda.bpm.client.ExternalTaskClient;

public class Application {
  private static final Random RANDOM = new Random();

  public static void main(String[] args) {
    ExternalTaskClient client = ExternalTaskClient.create()
        .baseUrl("http://localhost:8080/api")
        .maxTasks(1)
        .lockDuration(1000)
        .workerId("worker-id")
        .build();

    client.subscribe("decision")
        .lockDuration(1000)
        .handler((externalTask, externalTaskService) -> {
          boolean value = RANDOM.nextBoolean();
          externalTaskService.complete(
              externalTask,
              Collections.singletonMap("decision", value)
          );
          System.out.println("The External Task " + externalTask.getId() + " has been completed!");
        }).open();
  }
}
