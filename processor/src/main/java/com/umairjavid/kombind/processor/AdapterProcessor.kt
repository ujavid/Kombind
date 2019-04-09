package com.umairjavid.kombind.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import com.umairjavid.kombind.anontation.SimpleKombindAdapter
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.ElementFilter

@AutoService(Processor::class)
class AdapterProcessor : AbstractProcessor() {
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
        val kombindPackage = "com.umairjavid.kombind.ui"
        val appPackageName = processingEnv.elementUtils.getPackageOf(element).simpleName.toString()
        val kombindAdapterName = "KombindAdapter"
        val viewHolder = "KombindAdapter.ViewHolder"
        val fileName = element.simpleName
        val className = "Gen_${fileName}_Adapter"

        val file = FileSpec.builder(appPackageName, className)
                .addType(TypeSpec.classBuilder(className)
                        .primaryConstructor(FunSpec.constructorBuilder()
                                .addParameter("items", element.asType().asTypeName()).build()
                        )
                        .addSuperclassConstructorParameter("items")
                        .addFunction(FunSpec.builder("getLayout")
                                .addModifiers(KModifier.OVERRIDE)
                                .addParameter(ParameterSpec.builder("position", Int::class).build())
                                .addStatement("return $layoutRes")
                                .returns(Int::class)
                                .build())
                        .superclass(ClassName(kombindPackage, kombindAdapterName).parameterizedBy(ClassName(kombindPackage, viewHolder))

                        )
                        .build())
                .build()
        val kaptKotlnGenDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlnGenDir, "Gen$className"))
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}
