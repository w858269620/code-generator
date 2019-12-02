package cn.iba8.module.generator.common.properties2json.path;

import static cn.iba8.module.generator.common.properties2json.Constants.REGEX_DOT;

public class PathMetadataBuilder {

    public static PathMetadata createRootPathMetaData(String propertyKey) {
        String[] fields = propertyKey.split(REGEX_DOT);
        PathMetadata currentPathMetadata = null;

        for(int index = 0; index < fields.length; index++) {
            String field = fields[index];

            PathMetadata nextPathMetadata = new PathMetadata(propertyKey);
            nextPathMetadata.setParent(currentPathMetadata);
            nextPathMetadata.setFieldName(field);
            nextPathMetadata.setOriginalFieldName(field);

            if (currentPathMetadata != null) {
                currentPathMetadata.setChild(nextPathMetadata);
            }
            currentPathMetadata = nextPathMetadata;

        }
        return currentPathMetadata.getRoot();
    }
}
