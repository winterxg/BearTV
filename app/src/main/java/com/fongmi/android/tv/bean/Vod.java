package com.fongmi.android.tv.bean;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fongmi.android.tv.ui.base.ViewType;
import com.fongmi.android.tv.utils.Trans;
import com.fongmi.android.tv.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Root(strict = false)
public class Vod {

    @Element(name = "id", required = false)
    @SerializedName("vod_id")
    private String vodId;

    @Element(name = "name", required = false)
    @SerializedName("vod_name")
    private String vodName;

    @Element(name = "type", required = false)
    @SerializedName("type_name")
    private String typeName;

    @Element(name = "pic", required = false)
    @SerializedName("vod_pic")
    private String vodPic;

    @Element(name = "note", required = false)
    @SerializedName("vod_remarks")
    private String vodRemarks;

    @Element(name = "year", required = false)
    @SerializedName("vod_year")
    private String vodYear;

    @Element(name = "area", required = false)
    @SerializedName("vod_area")
    private String vodArea;

    @Element(name = "director", required = false)
    @SerializedName("vod_director")
    private String vodDirector;

    @Element(name = "actor", required = false)
    @SerializedName("vod_actor")
    private String vodActor;

    @Element(name = "des", required = false)
    @SerializedName("vod_content")
    private String vodContent;

    @SerializedName("vod_play_from")
    private String vodPlayFrom;

    @SerializedName("vod_play_url")
    private String vodPlayUrl;

    @SerializedName("vod_tag")
    private String vodTag;

    @SerializedName("style")
    private Style style;

    @Path("dl")
    @ElementList(entry = "dd", required = false, inline = true)
    private List<Flag> vodFlags;

    private Site site;

    public static List<Vod> arrayFrom(String str) {
        Type listType = new TypeToken<List<Vod>>() {
        }.getType();
        List<Vod> items = new Gson().fromJson(str, listType);
        return items == null ? Collections.emptyList() : items;
    }

    public String getVodId() {
        return TextUtils.isEmpty(vodId) ? "" : vodId.trim();
    }

    public void setVodId(String vodId) {
        this.vodId = vodId;
    }

    public String getVodName() {
        return TextUtils.isEmpty(vodName) ? "" : vodName.trim();
    }

    public void setVodName(String vodName) {
        this.vodName = vodName;
    }

    public String getTypeName() {
        return TextUtils.isEmpty(typeName) ? "" : typeName.trim();
    }

    public String getVodPic() {
        return TextUtils.isEmpty(vodPic) ? "" : vodPic.trim();
    }

    public void setVodPic(String vodPic) {
        this.vodPic = vodPic;
    }

    public String getVodRemarks() {
        return TextUtils.isEmpty(vodRemarks) ? "" : vodRemarks.trim();
    }

    public String getVodYear() {
        return TextUtils.isEmpty(vodYear) ? "" : vodYear.trim();
    }

    public String getVodArea() {
        return TextUtils.isEmpty(vodArea) ? "" : vodArea.trim();
    }

    public String getVodDirector() {
        return TextUtils.isEmpty(vodDirector) ? "" : vodDirector.trim();
    }

    public String getVodActor() {
        return TextUtils.isEmpty(vodActor) ? "" : vodActor.trim();
    }

    public String getVodContent() {
        return TextUtils.isEmpty(vodContent) ? "" : vodContent.trim().replace("\n", "<br>");
    }

    public String getVodPlayFrom() {
        return TextUtils.isEmpty(vodPlayFrom) ? "" : vodPlayFrom;
    }

    public String getVodPlayUrl() {
        return TextUtils.isEmpty(vodPlayUrl) ? "" : vodPlayUrl;
    }

    public String getVodTag() {
        return TextUtils.isEmpty(vodTag) ? "" : vodTag;
    }

    public Style getStyle() {
        return style;
    }

    public List<Flag> getVodFlags() {
        return vodFlags = vodFlags == null ? new ArrayList<>() : vodFlags;
    }

    public void setVodFlags(List<Flag> vodFlags) {
        this.vodFlags = vodFlags;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getSiteName() {
        return getSite() == null ? "" : getSite().getName();
    }

    public String getSiteKey() {
        return getSite() == null ? "" : getSite().getKey();
    }

    public int getSiteVisible() {
        return getSite() == null ? View.GONE : View.VISIBLE;
    }

    public int getYearVisible() {
        return getSite() != null || getVodYear().length() < 4 ? View.GONE : View.VISIBLE;
    }

    public int getRemarkVisible() {
        return getVodRemarks().isEmpty() ? View.GONE : View.VISIBLE;
    }

    public boolean isFolder() {
        return getVodTag().equals("folder");
    }

    public boolean isManga() {
        return getVodTag().equals("manga");
    }

    public Style getStyle(Style style) {
        return getStyle() == null ? style : getStyle();
    }

    public String getVodName(String name) {
        if (getVodName().isEmpty()) setVodName(name);
        return getVodName();
    }

    public void trans() {
        if (Trans.pass()) return;
        this.vodName = Trans.s2t(vodName);
        this.vodArea = Trans.s2t(vodArea);
        this.typeName = Trans.s2t(typeName);
        this.vodActor = Trans.s2t(vodActor);
        this.vodRemarks = Trans.s2t(vodRemarks);
        this.vodContent = Trans.s2t(vodContent);
        this.vodDirector = Trans.s2t(vodDirector);
    }

    public void setVodFlags() {
        String[] playFlags = getVodPlayFrom().split("\\$\\$\\$");
        String[] playUrls = getVodPlayUrl().split("\\$\\$\\$");
        for (int i = 0; i < playFlags.length; i++) {
            if (playFlags[i].isEmpty() || i >= playUrls.length) continue;
            Flag item = Flag.create(playFlags[i].trim());
            item.createEpisode(playUrls[i]);
            getVodFlags().add(item);
        }
        for (Flag item : getVodFlags()) {
            if (item.getUrls() == null) continue;
            item.createEpisode(item.getUrls());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vod)) return false;
        Vod it = (Vod) obj;
        return getVodId().equals(it.getVodId());
    }

    public static class Flag {

        @Attribute(name = "flag", required = false)
        @SerializedName("flag")
        private String flag;
        private String show;

        @Text
        private String urls;

        @SerializedName("episodes")
        private List<Episode> episodes;

        private boolean activated;
        private int position;

        public static Flag create(String flag) {
            return new Flag(flag);
        }

        public Flag() {
            this.episodes = new ArrayList<>();
            this.position = -1;
        }

        public Flag(String flag) {
            this.episodes = new ArrayList<>();
            this.show = Trans.s2t(flag);
            this.flag = flag;
            this.position = -1;
        }

        public String getShow() {
            return TextUtils.isEmpty(show) ? getFlag() : show;
        }

        public String getFlag() {
            return TextUtils.isEmpty(flag) ? "" : flag;
        }

        public String getUrls() {
            return urls;
        }

        public List<Episode> getEpisodes() {
            return episodes;
        }

        public boolean isActivated() {
            return activated;
        }

        public void setActivated(Flag item) {
            this.activated = item.equals(this);
            if (activated) item.episodes = episodes;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void createEpisode(String data) {
            String[] urls = data.contains("#") ? data.split("#") : new String[]{data};
            for (int i = 0; i < urls.length; i++) {
                String[] split = urls[i].split("\\$");
                String number = String.format(Locale.getDefault(), "%02d", i + 1);
                Episode episode = split.length > 1 ? Episode.create(split[0].isEmpty() ? number : split[0].trim(), split[1]) : Episode.create(number, urls[i]);
                if (!getEpisodes().contains(episode)) getEpisodes().add(episode);
            }
        }

        public void createEpisode(List<Episode> items) {
            getEpisodes().clear();
            getEpisodes().addAll(items);
        }

        public void toggle(boolean activated, Episode episode) {
            if (activated) setActivated(episode);
            else for (Episode item : getEpisodes()) item.deactivated();
        }

        private void setActivated(Episode episode) {
            setPosition(getEpisodes().indexOf(episode));
            for (int i = 0; i < getEpisodes().size(); i++) getEpisodes().get(i).setActivated(i == getPosition());
        }

        public Episode find(String remarks, boolean strict) {
            int number = Utils.getDigit(remarks);
            if (getEpisodes().size() == 0) return null;
            if (getEpisodes().size() == 1) return getEpisodes().get(0);
            for (Episode item : getEpisodes()) if (item.rule1(remarks)) return item;
            for (Episode item : getEpisodes()) if (item.rule2(number)) return item;
            for (Episode item : getEpisodes()) if (item.rule3(remarks)) return item;
            for (Episode item : getEpisodes()) if (item.rule4(remarks)) return item;
            if (getPosition() != -1) return getEpisodes().get(getPosition());
            return strict ? null : getEpisodes().get(0);
        }

        public static List<Flag> create(String flag, String name, String url) {
            Flag item = Flag.create(flag);
            item.getEpisodes().add(Episode.create(name, url));
            return Arrays.asList(item);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Flag)) return false;
            Flag it = (Flag) obj;
            return getFlag().equals(it.getFlag());
        }

        @NonNull
        @Override
        public String toString() {
            return new Gson().toJson(this);
        }

        public static class Episode {

            @SerializedName("name")
            private String name;
            @SerializedName("desc")
            private String desc;
            @SerializedName("url")
            private String url;

            private final int number;
            private boolean activated;

            public static Episode create(String name, String url) {
                return new Episode(name, "", url);
            }

            public static Episode create(String name, String desc, String url) {
                return new Episode(name, desc, url);
            }

            public static Episode objectFrom(String str) {
                return new Gson().fromJson(str, Episode.class);
            }

            public static List<Episode> arrayFrom(String str) {
                Type listType = new TypeToken<List<Episode>>() {}.getType();
                List<Episode> items = new Gson().fromJson(str, listType);
                return items == null ? Collections.emptyList() : items;
            }

            public Episode(String name, String desc, String url) {
                this.number = Utils.getDigit(name);
                this.name = Trans.s2t(name);
                this.desc = Trans.s2t(desc);
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public String getUrl() {
                return url;
            }

            public int getNumber() {
                return number;
            }

            public boolean isActivated() {
                return activated;
            }

            public void deactivated() {
                this.activated = false;
            }

            public void setActivated(boolean activated) {
                this.activated = activated;
            }

            public boolean rule1(String name) {
                return getName().equalsIgnoreCase(name);
            }

            public boolean rule2(int number) {
                return getNumber() == number && number != -1;
            }

            public boolean rule3(String name) {
                return getName().toLowerCase().contains(name.toLowerCase());
            }

            public boolean rule4(String name) {
                return name.toLowerCase().contains(getName().toLowerCase());
            }

            public boolean equals(Episode episode) {
                return rule1(episode.getName());
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (!(obj instanceof Episode)) return false;
                Episode it = (Episode) obj;
                return getUrl().equals(it.getUrl()) && getName().equals(it.getName());
            }

            public static class Sorter implements Comparator<Episode> {

                public static List<Episode> sort(List<Episode> items) {
                    if (items.size() > 1) Collections.sort(items, new Sorter());
                    return items;
                }

                @Override
                public int compare(Episode o1, Episode o2) {
                    return Integer.compare(o1.getNumber(), o2.getNumber());
                }
            }
        }
    }

    public static class Style {

        @SerializedName("type")
        private final String type;
        @SerializedName("ratio")
        private Float ratio;

        public static Style rect() {
            return new Style("rect", 0.75f);
        }

        public static Style list() {
            return new Style("list");
        }

        public Style(String type) {
            this.type = type;
        }

        public Style(String type, Float ratio) {
            this.type = type;
            this.ratio = ratio;
        }

        public String getType() {
            return TextUtils.isEmpty(type) ? "rect" : type;
        }

        public Float getRatio() {
            return ratio == null || ratio <= 0 ? (isOval() ? 1.0f : 0.75f) : Math.min(4, ratio);
        }

        public boolean isRect() {
            return getType().equals("rect");
        }

        public boolean isOval() {
            return getType().equals("oval");
        }

        public boolean isList() {
            return getType().equals("list");
        }

        public boolean isLand() {
            return isRect() && getRatio() > 1.0f;
        }

        public int getViewType() {
            switch (getType()) {
                case "oval":
                    return ViewType.OVAL;
                case "list":
                    return ViewType.LIST;
                default:
                    return ViewType.RECT;
            }
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Style)) return false;
            Style it = (Style) obj;
            return getType().equals(it.getType()) && getRatio().equals(it.getRatio());
        }
    }
}
