DEPENDS += "json-c curl"

do_configure:prepend() {
    (  cd ${S}
    ${S}/bootstrap)
}

FILES_${PN} += "${sysconfdir}"
