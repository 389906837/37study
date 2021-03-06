package ${info.daoPackage};

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cang.os.common.dao.BaseMongoDao;
import ${info.modelPackage}.${info.modelName};

/** ${info.tableComment}(${info.tableName}) **/
public interface ${info.daoName} extends BaseMongoDao<${info.modelName}> {


	/** codegen **/
}
