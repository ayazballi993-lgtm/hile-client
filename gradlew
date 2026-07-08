#!/bin/sh

#
# Telif hakkı © 2015 orijinal yazarlara aittir.
#
# Apache Lisansı, Sürüm 2.0 ("Lisans") kapsamında lisanslanmıştır;
# Bu dosyayı, Lisansa uygunluk dışında kullanamazsınız.
Lisansın bir kopyasını şu adresten edinebilirsiniz:
#
# https://www.apache.org/licenses/LICENSE-2.0
#
# Yürürlükteki yasalar gerektirmediği veya yazılı olarak kararlaştırılmadığı sürece, yazılım
# Bu lisans kapsamında dağıtılan ürün "olduğu gibi" esasına göre dağıtılmaktadır.
# Herhangi bir garanti veya koşul verilmemektedir, ne açık ne de örtülü.
# Belirli dil izinleri için Lisansa bakın ve
Lisans kapsamındaki sınırlamalar.
#
# SPDX-Lisans-Tanımlayıcısı: Apache-2.0
#

##############################################################################
#
# Gradle tarafından oluşturulan POSIX için Gradle başlatma betiği.
#
# Çalıştırmak için önemli:
#
# (1) Bu betiği çalıştırmak için POSIX uyumlu bir kabuğa ihtiyacınız var. Eğer /bin/sh'niz
# Uyumsuz, ancak ksh gibi uyumlu başka bir kabuk hizmetiniz var.
# bash kullanıyorsanız, bu betiği çalıştırmak için tüm komut dosyasının başına bash kabuk adını yazın.
# komut satırı, örneğin:
#
# ksh Gradle
#
# Busybox ve benzeri basitleştirilmiş kabuklar bu betik nedeniyle ÇALIŞMAYACAKTIR.
# Aşağıdaki POSIX kabuk özelliklerinin tümünü gerektirir:
# * fonksiyonlar;
# * genişletmeler «$var», «${var}», «${var:-default}», «${var+SET}»,
# «${var#prefix}», «${var%suffix}» ve «$( cmd )»;
# * Özellikle «case» olmak üzere, test edilebilir çıkış durumuna sahip bileşik komutlar;
# * «command», «set» ve «ulimit» dahil olmak üzere çeşitli yerleşik komutlar.
#
# Yama işlemi için önemli:
#
# (2) Bu komut dosyası herhangi bir POSIX kabuğunu hedef alır, bu nedenle sağlanan uzantılardan kaçınır.
# Bash, Ksh vb. tarafından; özellikle dizilerden kaçınılır.
#
# Birden fazla parametreyi bir araya getirme "geleneksel" uygulaması
# Boşlukla ayrılmış dizeler, iyi belgelenmiş bir hata ve güvenlik açığı kaynağıdır.
# sorunlar, bu nedenle bu durum (çoğunlukla) kademeli olarak biriktirilerek önlenir.
"$@" içindeki seçenekler ve bunların nihayetinde Java'ya iletilmesi.
#
# Miras alınan ortam değişkenlerinin (DEFAULT_JVM_OPTS, JAVA_OPTS,
# ve GRADLE_OPTS) kelime bölme işlemine dayanır, bu işlem açıkça gerçekleştirilir;
# Ayrıntılar için satır içi yorumlara bakın.
#
# AIX, CygWin gibi belirli işletim sistemleri için bazı ince ayarlar mevcuttur.
# Darwin, MinGW ve NonStop.
#
# (3) Bu komut dosyası Groovy şablonundan oluşturulmuştur
# https://github.com/gradle/gradle/blob/3d91ce3b8caaf77ad09f381f43615b715b53f72c/platforms/jvm/plugins-application/src/main/resources/org/gradle/api/internal/plugins/unixStartScript.txt
# Gradle projesi içerisinde.
#
Gradle'ı https://github.com/gradle/gradle/ adresinde bulabilirsiniz.
#
##############################################################################

# APP_HOME'u ayarlama girişimi

# Bağlantıları çözümle: $0 bir bağlantı olabilir
uygulama_yolu=$0

# Zincirleme sembolik bağlantılar için buna ihtiyaç var.
sırasında
    APP_HOME=${app_path%"${app_path##*/}"} # sonunda / bırakır; önde yol yoksa boş bırakılır;
    [ -h "$app_path" ]
Yapmak
    ls=$( ls -ld "$app_path" )
    bağlantı=${ls#*' -> '}
    durum $link içinde #(
      /*) app_path=$link ;; #(
      *) app_path=$APP_HOME$link ;;
    esac
Tamamlandı

# Bu normalde kullanılmaz
# shellcheck disable=SC2034
APP_BASE_NAME=${0##*/}
# $CDPATH ayarlanmışsa cd standart çıktısını yok say (https://github.com/gradle/gradle/issues/25036)
APP_HOME=$( cd -P "${APP_HOME:-./}" > /dev/null && printf '%s\n' "$PWD" ) || exit

# Mevcut maksimum değeri kullanın veya MAX_FD != -1 olarak ayarlayarak o değeri kullanın.
MAX_FD=maksimum

uyarmak () {
    yankıla "$*"
} >&2

ölmek () {
    yankı
    yankıla "$*"
    yankı
    çıkış 1
} >&2

# İşletim sistemine özgü destek ('true' veya 'false' olmalıdır).
cygwin=false
msys=false
darwin=false
kesintisiz=yanlış
durum "$( uname )" içinde #(
  CYGWIN* ) cygwin=true ;; #(
  Darwin* ) darwin=true ;; #(
  MSYS* | MINGW* ) msys=true ;; #(
  KESİNTİSİZ* ) kesintisiz=doğru ;;
esac



# JVM'yi başlatmak için kullanılacak Java komutunu belirleyin.
eğer [ -n "$JAVA_HOME" ] ise; o zaman
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM'in AIX üzerindeki JDK'sı, çalıştırılabilir dosyalar için garip konumlar kullanıyor
        JAVACMD=$JAVA_HOME/jre/sh/java
    başka
        JAVACMD=$JAVA_HOME/bin/java
    fi
    if [ ! -x "$JAVACMD" ] ; then
        "HATA: JAVA_HOME geçersiz bir dizine ayarlanmış: $JAVA_HOME"

Lütfen ortamınızdaki JAVA_HOME değişkenini aşağıdakiyle eşleşecek şekilde ayarlayın.
Java kurulumunuzun konumu."
    fi
başka
    JAVACMD=java
    if ! command -v java >/dev/null 2>&1
    Daha sonra
        "HATA: JAVA_HOME ayarlanmamış ve PATH'inizde 'java' komutu bulunamadı."

Lütfen ortamınızdaki JAVA_HOME değişkenini aşağıdakiyle eşleşecek şekilde ayarlayın.
Java kurulumunuzun konumu."
    fi
fi

# Mümkünse maksimum dosya tanımlayıcı sayısını artırın.
Eğer "$cygwin" ve "$darwin" ve "$nonstop" doğruysa; o zaman
    durum $MAX_FD içinde #(
      maksimum*)
        # POSIX sh'de ulimit -H tanımsızdır. Bu yüzden çalışıp çalışmadığı kontrol edilir.
        # shellcheck disable=SC2039,SC3045
        MAX_FD=$( ulimit -H -n ) ||
            "Maksimum dosya tanımlayıcı sınırına sorgu yapılamadı" uyarısı
    esac
    durum $MAX_FD içinde #(
      '' | yumuşak) :;; #(
      *)
        # POSIX sh'de ulimit -n tanımsızdır. Bu yüzden çalışıp çalışmadığı kontrol edilir.
        # shellcheck disable=SC2039,SC3045
        ulimit -n "$MAX_FD" ||
            "Maksimum dosya tanımlayıcı sınırını $MAX_FD olarak ayarlayamadı" uyarısı
    esac
fi

# Java komutu için tüm argümanları ters sırada toplayın:
# * komut satırından gelen argümanlar
# * ana sınıf adı
# * -sınıf yolu
# * -D...uygulama adı ayarları
# * --modül-yolu (yalnızca gerekirse)
# * DEFAULT_JVM_OPTS, JAVA_OPTS ve GRADLE_OPTS ortam değişkenleri.

# Cygwin veya MSYS için, Java çalıştırmadan önce yolları Windows formatına dönüştürün.
eğer "$cygwin" || "$msys" ise; o zaman
    APP_HOME=$( cygpath --path --mixed "$APP_HOME" )

    JAVACMD=$( cygpath --unix "$JAVACMD" )

    # Şimdi argümanları dönüştürün - kendimizi /bin/sh ile sınırlamak için geçici çözüm
    arg için yap
        eğer
            durum $arg içinde #(
              -*) yanlış ;; # seçeneklerle oynamayın #(
              /?*) t=${arg#/} t=/${t%%/*} # POSIX dosya yoluna benziyor
                    [ -e "$t" ] ;; #(
              *) YANLIŞ ;;
            esac
        Daha sonra
            arg=$( cygpath --path --ignore --mixed "$arg" )
        fi
        # Argüman listesini, tam olarak sayı kadar kez döndürün.
        # argümanlar, bu nedenle her argüman başladığı konuma geri döner, ancak
        # muhtemelen değiştirildi.
        #
        # Not: `for` döngüsü başlamadan önce yineleme listesini yakalar, bu nedenle
        # Burada konum parametrelerini değiştirmek, sayıyı etkilemez
        # yinelemeler, ne de `arg`'da sunulan değerler.
        kaydırma # eski argümanı kaldır
        set -- "$@" "$arg" # değiştirme argümanını gönder
    Tamamlandı
fi


# Varsayılan JVM seçeneklerini buraya ekleyin. Bu betiğe JVM seçeneklerini iletmek için JAVA_OPTS ve GRADLE_OPTS'yi de kullanabilirsiniz.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Java komutu için tüm argümanları toplayın:
# * DEFAULT_JVM_OPTS, JAVA_OPTS ve optsEnvironmentVar'ın shell parçaları içermesine izin verilmez,
# ve içine yerleştirilmiş tüm shell kodları kaçış karakteri olarak kullanılacaktır.
# * Örneğin: Bir kullanıcı, ${Hostname}'in genişletilmesini bekleyemez, çünkü bu bir ortam değişkenidir ve şu şekilde olacaktır:
# Komut satırında '${Hostname}' olarak ele alınır.

ayarlamak -- \
        "-Dorg.gradle.appname=$APP_BASE_NAME" \
        -jar "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" \
        "$@"

# "xargs" kullanılamadığında dur.
if ! command -v xargs >/dev/null 2>&1
Daha sonra
    "xargs kullanılamıyor" hatası
fi

# Tırnak içinde belirtilen argümanları ayrıştırmak için "xargs" kullanın.
#
# -n1 seçeneğiyle, tırnak işaretleri ve ters eğik çizgiler kaldırılarak her satıra bir argüman yazdırılır.
#
# Bash'te bunu şu şekilde yapabiliriz:
#
# readarray ARGS < <( xargs -n1 <<<"$var" ) &&
# set -- "${ARGS[@]}" "$@"
#
# Ancak POSIX kabuğunda ne diziler ne de komut ikamesi bulunmadığından, bunun yerine biz
# Her bir argümanı (sed'e girdi satırı olarak) ters eğik çizgiyle kaçış karakteri ekleyerek işleyin.
# Bu karakter bir kabuk meta karakteri olabilir, ardından tersine çevirmek için eval kullanın.
# Bu işlemi (argümanlar arasındaki ayrımı koruyarak) gerçekleştirin ve sarmalayın.
# Tümünü tek bir "set" ifadesi olarak düzenleyin.
#
# Bu, elbette bu değişkenlerden herhangi biri yeni satır karakteri içeriyorsa bozulacaktır.
# Eşsiz bir alıntı.
#

değerlendirme "ayarla -- $(
        printf '%s\n' "$DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS" |
        xargs -n1 |
        sed ' s~[^-[:alnum:]+,./:=@_]~\\&~g; ' |
        tr '\n' ' '
    )" '"$@"'

exec "$JAVACMD" "$@"
