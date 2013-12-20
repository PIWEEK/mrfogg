package org.mrfogg.widget.image

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.assets.AssetsBundle

import org.mrfogg.MrFoggConfiguration
import org.mrfogg.widget.WidgetProvider
import org.mrfogg.widget.WidgetData
import org.mrfogg.widget.image.resources.ImageWidgetResource
import org.mrfogg.widget.image.resources.VideoWidgetResource

public class MrFoggWidgetImageService extends Service<MrFoggConfiguration> implements WidgetProvider{
    public static void main(String[] args) throws Exception {
        new MrFoggWidgetImageService().run(args);
    }

    @Override
    public void initialize(Bootstrap<MrFoggConfiguration> bootstrap) {
        bootstrap.setName("widget-images");
        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets"));
    }

    @Override
    public void run(MrFoggConfiguration configuration,Environment environment) {
        environment.addResource(new ImageWidgetResource())
        environment.addResource(new VideoWidgetResource())
    }

    @Override
    public WidgetData getWidgetData() {
        return new WidgetData([
            name: "Images Widget"
        ])
    }
}
