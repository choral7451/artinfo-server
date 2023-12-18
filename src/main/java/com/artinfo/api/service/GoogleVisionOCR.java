package com.artinfo.api.service;


import com.google.cloud.vision.v1.*;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleVisionOCR {
  public static String execute (String url) throws IOException {
    StopWatch totalTime = new StopWatch();
    totalTime.start();

    List<AnnotateImageRequest> requests = new ArrayList<>();

    ImageSource imgSource = ImageSource.newBuilder().setImageUri(url).build();
    Image img = Image.newBuilder().setSource(imgSource).build();
    Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
    AnnotateImageRequest request =
      AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
    requests.add(request);

    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();

      StringBuilder result = new StringBuilder();
      for (AnnotateImageResponse res : responses) {
        if (res.hasError()) {
          System.out.format("Error: %s%n", res.getError().getMessage());
          return null;
        }

        for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
          result.append(annotation.getDescription()).append(" ");
        }
      }

      totalTime.stop();
      System.out.println("Total Time : " + totalTime.getTime() + "ms");

      return result.toString();
    }
    catch (Exception exception) {
      return exception.getMessage();
    }
  }
}
