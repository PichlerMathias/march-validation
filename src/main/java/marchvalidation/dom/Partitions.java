package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/** Maps to PartitionsDto */
public interface Partitions extends DomElement {
    @SubTagList("partition")
    List<Partition> getPartitions();
}