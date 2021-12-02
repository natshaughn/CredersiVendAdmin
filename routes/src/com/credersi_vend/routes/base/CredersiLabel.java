package com.credersi_vend.routes.base;

import java.util.List;

public abstract class CredersiLabel extends CredersiBase {
    abstract public String getLabel();
    abstract public String toProps(String alias, List<String> params);

    protected String asJson(Object[] pairs) {
        String json = String.format("{\"label\": \"%s\"", this.getLabel());
        
        for (int i = 0; i < pairs.length; i += 2) {
            if (pairs[i+1] == null) {
                continue;
            }

            String key = pairs[i].toString();
            String value = pairs[i+1].toString();
            
            if (value.length() < 1) {
                continue;
            }
            
            // 
            // NOTE: All are string based at time of writing
            // TODO: Still need to escape string values for JSON
            // 
            
            json += String.format(", \"%s\": \"%s\"", key, value);
        }
        
        return String.format("%s}", json);
    }
    
    protected String asProps(String alias, Object[] pairs, List<String> params) {
        boolean first = true;
        String props = "";
        
        for (int i = 0; i < pairs.length; i += 2) {
            if (pairs[i+1] == null) {
                continue;
            }

            String key = pairs[i].toString();
            String value = pairs[i+1].toString();
            
            if (value.length() < 1) {
                continue;
            }

            String param = String.format("%s_%s", alias, key);            
            String prop = String.format("%s: $%s", key, param);
            props += (first) ? prop : String.format(", %s", prop);

            params.add(param);
            params.add(value);

            first = false;
        }
        
        return (first) ? "" : String.format(" {%s}", props);
    }

    public boolean setProp(String key, String value) {
        return false;
    }
}