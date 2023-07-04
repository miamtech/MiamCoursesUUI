package tech.miam.coursesUDemoApp.miam.template

import com.miam.sdk.templatesConfigs.MiamTheme
import com.miam.sdk.templatesConfigs.MiamTheme.mealPlanner
import tech.miam.coursesuui.template.mealPlanner.callToAction.CallToActionU
import tech.miam.coursesuui.template.mealPlanner.form.CoursesUBudgetForm


class MiamTemplateManager {
    init {
        MiamTheme.Template {

            mealPlanner {
                callToActionConfig {
                    view = CallToActionU()
                }
                formConfig {
                    view = CoursesUBudgetForm()
                }
            }
        }
    }
}