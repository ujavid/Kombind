package com.umairjavid.kombind.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.umairjavid.kombind.anontation.SimpleKombindAdapter
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.tools.Diagnostic
import kotlin.reflect.KClass

@AutoService(Processor::class)
class AdapterProcessor : AbstractProcessor() {
    lateinit var messenge: Messager

    override fun init(env: ProcessingEnvironment?) {
        super.init(env)
        messenge = env!!.messager
    }
    override fun getSupportedAnnotationTypes() = mutableSetOf(SimpleKombindAdapter::class.java.name)

    override fun getSupportedSourceVersion() = SourceVersion.latestSupported()

    override fun process(typeElemnts: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val env = roundEnv!!.getElementsAnnotatedWith(SimpleKombindAdapter::class.java)
        env.forEach {
            val layoutRes = it.getAnnotation(SimpleKombindAdapter::class.java).layoutRes
            if (it is VariableElement)
                generateAdapterClass(it, layoutRes)
        }
        return false
    }

    fun generateAdapterClass(element: VariableElement, layoutRes: Int) {
        if (!element.asType().asTypeName().isMutableLiveArraylist()) {
            messenge.printMessage(Diagnostic.Kind.ERROR, "Needs to be of type MutableArrayList")
            return
        }

        val kombindPackage = "com.umairjavid.kombind.ui"
        val appPackageName = processingEnv.elementUtils.getPackageOf(element).simpleName.toString()
        val kombindAdapterName = "KombindAdapter"
        val viewHolder = "KombindAdapter.ViewHolder"
        val elementName = element.simpleName
        val className = "Kombind${elementName.toString().capitalize()}Adapter"
        val file = FileSpec.builder(appPackageName, className)
                .addType(TypeSpec.classBuilder(className)
                        .makeAbstractIfTrue(layoutRes == 0)
                        .primaryConstructor(FunSpec.constructorBuilder()
                                .addParameter("items", element.asType().asTypeName().checkForAnyType())
                                .addParameter("handler", Any::class).build())
                        .addProperty(generateConstructorProperty("handler", Any::class))
                        .addSuperclassConstructorParameter("items")
                        .addFunction(generateGetHandlerMethod())
                        .addFunction(if (layoutRes == 0) generateAbstractGetLayoutMethod() else generateGetLayoutMethod(layoutRes))
                        .superclass(ClassName(kombindPackage, kombindAdapterName).parameterizedBy(ClassName(kombindPackage, viewHolder))
                        ).build())
                .build()
        val kaptKotlnGenDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlnGenDir, "Gen$className"))
    }

    private fun generateConstructorProperty(propertyName: String,  propertyType: KClass<*>) = PropertySpec.builder(propertyName, propertyType)
            .initializer(propertyName)
            .addModifiers(KModifier.PRIVATE)
            .build()

    private fun generateGetHandlerMethod() = FunSpec.builder("getHandler")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(ParameterSpec.builder("position", Int::class).build())
            .addStatement("return handler")
            .build()

    private fun generateGetLayoutMethod(layoutRes: Int): FunSpec {
       return FunSpec.builder("getLayout")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter(ParameterSpec.builder("position", Int::class).build())
                .addStatement("return $layoutRes")
                .returns(Int::class)
                .build()
    }

    private fun generateAbstractGetLayoutMethod() =
            FunSpec.builder("getLayout")
            .addModifiers(KModifier.OVERRIDE, KModifier.ABSTRACT)
            .addParameter(ParameterSpec.builder("position", Int::class).build())
            .returns(Int::class)
            .build()

    private fun String.capitalize() = this.substring(0..0).toUpperCase() + this.substring(1)


    private fun TypeName.checkForAnyType() = if (this.toString().equals("com.umairjavid.kombind.model.MutableLiveArrayList<java.lang.Object>")) {
        ClassName("com.umairjavid.kombind.model", "MutableLiveArrayList").parameterizedBy(ClassName("kotlin", "Any"))
    } else this

    private fun TypeSpec.Builder.makeAbstractIfTrue(isAbstract: Boolean) = if (isAbstract) {

        this.addModifiers(KModifier.ABSTRACT)
    } else this

    private fun TypeName.isMutableLiveArraylist() = this.toString().startsWith("com.umairjavid.kombind.model.MutableLiveArrayList")

    companion object { const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated" }
}
