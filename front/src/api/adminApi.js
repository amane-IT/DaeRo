const HOST = 'https://i7D110.p.ssafy.io/api/'

const ADMIN = 'admin/'

export default {
    admin: {
        login: () => HOST + ADMIN + 'login',
        userInfo: () => HOST + ADMIN + 'users',
        userSearch: () => HOST + ADMIN + 'users/search',
        reportList: () => HOST + ADMIN + 'report',
        handledReport: reportSeq => HOST + ADMIN + `report/${reportSeq}`,
        suspend: userSeq => HOST + ADMIN + `user/${userSeq}/suspension`,
        articleList: () => HOST + ADMIN + 'article',
        articleSearch: () => HOST + ADMIN + 'article',
        articleDetail: articleSeq => HOST + ADMIN + `article/${articleSeq}`,
        replyList: articleSeq => HOST + ADMIN + `article/${articleSeq}/reply`,
        replyDetail: replySeq => HOST + ADMIN + `reply/${replySeq}`,
        deleteArticle: articleSeq => HOST + ADMIN + `article/${articleSeq}`,
        deleteReply: replySeq => HOST + ADMIN + `reply/${replySeq}`,
        placeList: () => HOST + ADMIN + 'place',
        placeDetail: placeSeq => HOST + ADMIN + `place/${placeSeq}`,
        createPlace: () => HOST + ADMIN + 'place',
        updatePlace: placeSeq => HOST + ADMIN + `place/${placeSeq}`,
        deletePlace: placeSeq => HOST + ADMIN + `place/${placeSeq}`,
        inquiryList: () => HOST + ADMIN + 'inquiry',
        inquiryDetail: inquirySeq => HOST + ADMIN + `inquiry/${inquirySeq}`,
        inquiryAnswer: inquirySeq => HOST + ADMIN + `inquiry/${inquirySeq}`,
        faqList: () => HOST + ADMIN + 'faq',
        createFaq: () => HOST + ADMIN + 'faq',
        updateFaq: faqSeq => HOST + ADMIN + `faq/${faqSeq}`,
        deleteFaq: faqSeq => HOST + ADMIN + `faq/${faqSeq}`,
        noticeList: () => HOST + ADMIN + 'notice',
        createNotice: () => HOST + ADMIN + 'notice',
        updateNotice: noticeSeq => HOST + ADMIN + `notice/${noticeSeq}`,
        deleteNotice: noticeSeq => HOST + ADMIN + `notice/${noticeSeq}`
    }
}