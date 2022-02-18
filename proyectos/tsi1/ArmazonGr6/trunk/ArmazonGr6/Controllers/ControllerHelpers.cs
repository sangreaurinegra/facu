using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ArmazonGr6.Models;

namespace ArmazonGr6.Controllers
{
    public static class ControllerHelpers
    {
        public static void AddReglasVioladas(this System.Web.Mvc.ModelStateDictionary modelState,
            IEnumerable<RuleViolation> errores)
        {
            foreach (RuleViolation detalle in errores)
            {
                modelState.AddModelError(detalle.PropertyName, detalle.ErrorMessage);
            }
        }
    }
}
