package marchvalidation.dom.util;

import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.DomUtil;
import marchvalidation.dom.MarchConfigRoot;

public class MarchConfigUtil {

    public static MarchConfigRoot getRoot(final ConvertContext context) {
        return (MarchConfigRoot) DomUtil.getFileElement(context.getInvocationElement()).getRootElement();
    }
}
