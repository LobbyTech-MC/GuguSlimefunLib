package me.ddggdd135.guguslimefunlib.script;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import org.graalvm.polyglot.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.script.ScriptException;
import java.io.File;

public class JavaScriptEval extends ScriptEval {

    private GraalJSScriptEngine jsEngine;

    public JavaScriptEval(@NotNull File js) {
        super(js);
        reSetup();
        setup();
        contextInit();
        doInit();
    }

    public JavaScriptEval(@NotNull String js) {
        super(js);
        reSetup();
        setup();
        contextInit();
        doInit();
    }

    private void advancedSetup() {
        JSRealm realm = JavaScriptLanguage.getJSRealm(jsEngine.getPolyglotContext());
        TruffleLanguage.Env env = realm.getEnv();
        addThing("SlimefunItems", env.asHostSymbol(SlimefunItems.class));
        addThing("SlimefunItem", env.asHostSymbol(SlimefunItem.class));
        addThing("StorageCacheUtils", env.asHostSymbol(StorageCacheUtils.class));
        addThing("SlimefunUtils", env.asHostSymbol(SlimefunUtils.class));
    }

    @Override
    public void addThing(String name, Object value) {
        jsEngine.put(name, value);
    }

    @Override
    public String key() {
        return "js";
    }

    protected final void contextInit() {
        if (jsEngine != null) {
            try {
                jsEngine.eval(getFileContext());
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public Object evalFunction(String funName, Object... args) {
        if (getFileContext() == null || getFileContext().isBlank()) {
            contextInit();
        }

        try {
            return jsEngine.invokeFunction(funName, args);
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void reSetup() {
        jsEngine = GraalJSScriptEngine.create(
                null,
                Context.newBuilder("js")
                        .allowAllAccess(true));

        advancedSetup();
    }
}
