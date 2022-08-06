package java_itamae.domain.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
public class GetTestContents implements Supplier<List<String>> {

  @Override
  public List<String> get() {
    final List<String> contents = new ArrayList<>();
    contents.add("1 行目");
    contents.add("2 行目");

    return contents;
  }
}
