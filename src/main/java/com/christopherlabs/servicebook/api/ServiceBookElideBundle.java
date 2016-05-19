package com.christopherlabs.servicebook.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahoo.elide.contrib.dropwizard.elide.ElideBundle;
import com.yahoo.elide.core.DataStore;
import com.yahoo.elide.core.EntityDictionary;
import com.yahoo.elide.datastores.hibernate5.HibernateStore;
import com.yahoo.elide.jsonapi.JsonApiMapper;
import com.yahoo.elide.utils.coerce.CoerceUtil;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.setup.Environment;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.hibernate.SessionFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by cwilliamson on 5/18/16.
 */
public class ServiceBookElideBundle extends ElideBundle<ServicebookApiConfiguration> {

    @Getter
    @Setter
    private DataStore ds;
    private EntityDictionary dictionary;

    public ServiceBookElideBundle(Class<?> entity, Class<?>... entities) {
        super(entity, entities);
        dictionary = new EntityDictionary(new HashMap());
    }

    public PooledDataSourceFactory getDataSourceFactory(ServicebookApiConfiguration servicebookApiConfiguration) {
        return servicebookApiConfiguration.getDataSourceFactory();
    }

    public DataStore getDataStore(ServicebookApiConfiguration configuration, Environment environment) {
        if (ds == null) {
            final PooledDataSourceFactory dbConfig = getDataSourceFactory(configuration);
            SessionFactory sessionFactory = getSessionFactoryFactory().build(this, environment, dbConfig, getEntities(), name());
            ds = new HibernateStore(sessionFactory);
        }
        ds.populateEntityDictionary(dictionary);
        return ds;
    }

    public JsonApiMapper getJsonApiMapper(ServicebookApiConfiguration configuration, Environment environment) {

        JsonApiMapper jsonApiMapper = new JsonApiMapper(dictionary);

        // Serialize dates into ISO8601 form
        ObjectMapper mapper = jsonApiMapper.getObjectMapper();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        mapper.setDateFormat(myDateFormat);

        // Hack to initialize CoerceUtil
        CoerceUtil.coerce("blah", String.class);

        // Deserialize dates from ISO8601 form
        DateTimeConverter dateTimeConverter = new DateConverter();
        dateTimeConverter.setPattern("yyyy-MM-dd'T'HH:mm:ssX");
        BeanUtilsBean.getInstance().getConvertUtils().register(dateTimeConverter, Date.class);

        return jsonApiMapper;
    }
}
