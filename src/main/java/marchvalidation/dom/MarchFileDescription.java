package marchvalidation.dom;

import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileDescription;
import com.intellij.openapi.module.Module;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MarchFileDescription extends DomFileDescription<MarchConfigRoot> {
    public MarchFileDescription() {
        super(MarchConfigRoot.class, "march");
    }

    @Override
    public boolean isMyFile(final @NotNull XmlFile file, final @Nullable Module module) {
        final var rootTag = file.getRootTag();
        return rootTag != null && "march".equalsIgnoreCase(rootTag.getLocalName());
    }
}